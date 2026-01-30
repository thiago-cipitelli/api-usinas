package com.boltenergy.api_usinas.controllers

import com.boltenergy.api_usinas.repositories.RankingPotencia
import com.boltenergy.api_usinas.repositories.UsinaRepository
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import com.boltenergy.api_usinas.services.RalieUsinasService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping

@RestController
@RequestMapping("/usinas")
@CrossOrigin(origins = ["http://localhost:4200"])
class UsinaController(
    private val ralieUsinas: RalieUsinasService,
    private val usinaRepository: UsinaRepository
) {

    @GetMapping("/ranking-potencia")
    fun getRankingPotencia(): ResponseEntity<List<RankingPotencia>> {
        val ranking = usinaRepository.findTop5UsinasMaisPotentes()

        return if (ranking.isEmpty()) {
            ResponseEntity.noContent().build()
        } else {
            ResponseEntity.ok(ranking)
        }
    }
}