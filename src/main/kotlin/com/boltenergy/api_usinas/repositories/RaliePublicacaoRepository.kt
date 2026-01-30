package com.boltenergy.api_usinas.repositories

import com.boltenergy.api_usinas.models.RaliePublicacao
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
interface RaliePublicacaoRepository : JpaRepository<RaliePublicacao, Long> {
    fun findByUsinaCeg(ceg: String): List<RaliePublicacao>

    fun findByUsinaCegAndDataPublicacaoBetween(
        ceg: String,
        startDate: LocalDate,
        endDate: LocalDate
    ): List<RaliePublicacao>

    fun findByUsinaCegAndDataPublicacao(ceg: String, dataPublicacao: LocalDate): RaliePublicacao?

    @Query("SELECT CONCAT(r.usina.ceg, '_', r.dataPublicacao) FROM RaliePublicacao r WHERE r.usina.ceg IN :cegs")
    fun findAllKeysByUsinaCegIn(cegs: Set<String>): List<String>
}