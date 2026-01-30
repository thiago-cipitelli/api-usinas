package com.boltenergy.api_usinas.controllers

import com.boltenergy.api_usinas.repositories.RankingPotencia
import com.boltenergy.api_usinas.repositories.UsinaRepository
import com.boltenergy.api_usinas.services.RalieUsinasService
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.aot.hint.TypeReference.listOf
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import com.ninjasquad.springmockk.MockkBean
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.math.BigDecimal
import java.time.LocalDate
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get


@WebMvcTest(UsinaController::class)
class UsinaControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockkBean
    private lateinit var usinaRepository: UsinaRepository

    @MockkBean
    private lateinit var ralieUsinas: RalieUsinasService

    @Test
    fun `deve retornar 200 OK quando existir ranking de potencia`() {
        val ranking = listOf(
            object : RankingPotencia {
                override val nome = "Usina A"
                override val ceg = "CEG123"
                override val potencia = BigDecimal("1000.00")
                override val dataRalie = LocalDate.of(2024, 1, 10)
            }
        )

        every { usinaRepository.findTop5UsinasMaisPotentes() } returns ranking

        mockMvc.perform(get("/usinas/ranking-potencia"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.length()").value(1))
            .andExpect(jsonPath("$[0].nome").value("Usina A"))
            .andExpect(jsonPath("$[0].ceg").value("CEG123"))
            .andExpect(jsonPath("$[0].potencia").value(1000.00))
    }

    @Test
    fun `deve retornar 204 No Content quando ranking estiver vazio`() {
        every { usinaRepository.findTop5UsinasMaisPotentes() } returns emptyList()

        mockMvc.perform(get("/usinas/ranking-potencia"))
            .andExpect(status().isNoContent)
    }
}