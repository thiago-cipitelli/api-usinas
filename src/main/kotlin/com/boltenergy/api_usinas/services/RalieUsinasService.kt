package com.boltenergy.api_usinas.services

import com.boltenergy.api_usinas.repositories.RaliePublicacaoRepository
import com.boltenergy.api_usinas.models.RaliePublicacao
import com.boltenergy.api_usinas.models.Usina
import org.apache.commons.csv.CSVFormat
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import com.boltenergy.api_usinas.repositories.UsinaRepository
import com.boltenergy.api_usinas.schedulers.ProcessarDadosUsinas
import jakarta.transaction.Transactional
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.apache.commons.io.input.BOMInputStream
import org.slf4j.LoggerFactory
import org.springframework.core.io.buffer.DataBuffer
import org.springframework.core.io.buffer.DataBufferUtils
import org.springframework.transaction.annotation.Propagation
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Path
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.concurrent.ConcurrentHashMap

const val USINAS_CSV = "tmp/ralie-usinas.csv"

@Service
class RalieUsinasService(
    private val webClient: WebClient,
    private val usinaRepository: UsinaRepository,
    private val raliePublicacaoRepository: RaliePublicacaoRepository,
) {
    private val logger = LoggerFactory.getLogger(ProcessarDadosUsinas::class.java)
    fun downloadFile() {
        val path = Path.of(USINAS_CSV)

        Files.createDirectories(path.parent)

        val flux = webClient.get()
            .uri("https://dadosabertos.aneel.gov.br/dataset/57e4b8b5-a5db-40e6-9901-27ca629d0477/resource/4a615df8-4c25-48fa-bbea-873a36a79518/download/ralie-usina.csv")
            .retrieve()
            .bodyToFlux(DataBuffer::class.java)

        DataBufferUtils.write(flux, path)
            .block()
    }

    fun processarCsv() = runBlocking {
        val path = Path.of(USINAS_CSV)
        val batchSize = 10000

        val cegsNoArquivo = mutableSetOf<String>()
        Files.newBufferedReader(path, StandardCharsets.ISO_8859_1).use { reader ->
            CSVFormat.DEFAULT.builder()
                .setDelimiter(';')
                .setHeader().setSkipHeaderRecord(true).setIgnoreHeaderCase(true).build()
                .parse(reader).forEach { cegsNoArquivo.add(it["CodCEG"]) }
        }

        val cegeExistentes = ConcurrentHashMap.newKeySet<String>().apply {
            addAll(usinaRepository.findAllCegByCegIn(cegsNoArquivo))
        }
        val raliesExistentes = ConcurrentHashMap.newKeySet<String>().apply {
            addAll(raliePublicacaoRepository.findAllKeysByUsinaCegIn(cegsNoArquivo))
        }

        val allRecords = Files.newInputStream(path).use { inputStream ->
            val reader = BOMInputStream(inputStream).bufferedReader(StandardCharsets.ISO_8859_1)
            CSVFormat.DEFAULT.builder()
                .setDelimiter(';')
                .setHeader().setSkipHeaderRecord(true).setIgnoreHeaderCase(true).setTrim(true)
                .build().parse(reader).toList()
        }

        logger.info("Iniciando processamento paralelo...")
        val dbDispatcher = Dispatchers.IO.limitedParallelism(20)

        withContext(dbDispatcher) {
            allRecords.chunked(batchSize).map { chunk ->
                launch {
                    val batchUsina = mutableSetOf<Usina>()
                    val batchRalie = mutableSetOf<RaliePublicacao>()

                    chunk.forEach { record ->
                        val codCeg = record["CodCEG"]
                        val dataPub = LocalDate.parse(record["DatRalie"])
                        val chaveRalie = "${codCeg}_$dataPub"

                        if (cegeExistentes.add(codCeg)) {
                            batchUsina.add(Usina(
                                ceg = codCeg,
                                nome = record["NomEmpreendimento"],
                                uf = record["SigUFPrincipal"],
                                tipoGeracao = record["DscOrigemCombustivel"]
                            ))
                        }

                        if (raliesExistentes.add(chaveRalie)) {
                            val potencia = record["MdaPotenciaOutorgadaKw"]
                                .replace(".", "").replace(",", ".").toBigDecimal()

                            batchRalie.add(RaliePublicacao(
                                usina = Usina(ceg = codCeg),
                                dataPublicacao = dataPub,
                                potenciaOutorgada = potencia
                            ))
                        }
                    }
                    // Salva o lote deste chunk específico
                    flushData(batchUsina, batchRalie)
                }
            }.joinAll()
        }
        logger.info("Processamento paralelo concluído.")
    }

    @Transactional()
    private fun flushData(usinas: Set<Usina>, ralies: Set<RaliePublicacao>) {
        if (usinas.isNotEmpty()) {
            usinaRepository.saveAll(usinas)
        }
        if (ralies.isNotEmpty()) {
            raliePublicacaoRepository.saveAll(ralies)
        }
    }


}