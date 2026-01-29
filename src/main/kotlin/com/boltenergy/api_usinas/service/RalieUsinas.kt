package com.boltenergy.api_usinas.service

import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import java.nio.file.Files
import java.nio.file.Path

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

        Files.write(Path.of("tmp/ralie-usinas.csv"), bytes)
    }
}