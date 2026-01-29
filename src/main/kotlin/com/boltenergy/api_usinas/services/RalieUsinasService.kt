package com.boltenergy.api_usinas.services

import com.boltenergy.api_usinas.models.Usina
import org.apache.commons.csv.CSVFormat
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import com.boltenergy.api_usinas.repositories.UsinaRepository
import java.nio.file.Files
import java.nio.file.Path

const val USINAS_CSV = "tmp/ralie-usinas.csv"

@Service
class RalieUsinas(
    private val webClient: WebClient
) {
    fun downloadFile() {
        val bytes = webClient.get()
            .uri("https://dadosabertos.aneel.gov.br/dataset/57e4b8b5-a5db-40e6-9901-27ca629d0477/resource/4a615df8-4c25-48fa-bbea-873a36a79518/download/ralie-usina.csv")
            .retrieve()
            .bodyToMono(ByteArray::class.java)
            .block() ?: throw IllegalStateException("Falha ao baixar arquivo")

        Files.write(Path.of(USINAS_CSV), bytes)
    }

    fun processarCsv(
        usinaRepository: UsinaRepository
    ) {
        val path = Path.of(USINAS_CSV)

        Files.newBufferedReader(path).use { reader ->
            val csv = CSVFormat.DEFAULT
                .builder()
                .setHeader()
                .setSkipHeaderRecord(true)
                .build()
                .parse(reader)

            val usinas = csv.map { record ->
                Usina(
                    id = record["_id"].toLong(),
                    uf = record["SigUFPrincipal"],
                    origemCombustivel = record["DscOrigemCombustivel"],
                    potenciaKw = record["MdaPotenciaOutorgadaKw"].toDouble(),
                    nomeEmpreendimento = record["NomEmpreendimento"]
                )
            }

            usinaRepository.saveAll(usinas)


                // salvar no banco, enviar evento, etc

                // procurar o id no banco
                // se a DatGeracaoConjuntoDados for igual a do record, finalizar execucao (nao teve updates)
                // se for diferente:
                // atualizar registros
        }
    }
}