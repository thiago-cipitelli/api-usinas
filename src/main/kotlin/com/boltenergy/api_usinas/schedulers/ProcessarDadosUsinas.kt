package com.boltenergy.api_usinas.schedulers

import com.boltenergy.api_usinas.services.RalieUsinasService
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component


@Component
class ProcessarDadosUsinas (private val ralieUsinas: RalieUsinasService){
    @Scheduled(cron = "0 0 9 * * *")
    fun executar(){
        ralieUsinas.downloadFile()
        ralieUsinas.processarCsv(usinaRepository = )
    }
}