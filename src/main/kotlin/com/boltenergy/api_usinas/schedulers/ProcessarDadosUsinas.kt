package com.boltenergy.api_usinas.schedulers

import com.boltenergy.api_usinas.services.RalieUsinasService
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.slf4j.LoggerFactory


@Component
class ProcessarDadosUsinas (private val ralieUsinas: RalieUsinasService){

    private val logger = LoggerFactory.getLogger(ProcessarDadosUsinas::class.java)

    @Scheduled(cron = "0 0 9 * * *")
    fun executar(){
        try {
            logger.info("Iniciando processamento das usinas")
            ralieUsinas.downloadFile()
            logger.info("Processamento finalizado")
        } catch (ex: Exception) {
            logger.error("Erro ao processar usinas", ex)
        }
    }
}