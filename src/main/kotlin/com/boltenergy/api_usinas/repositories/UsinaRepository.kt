package com.boltenergy.api_usinas.repositories

import com.boltenergy.api_usinas.models.Usina
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.math.BigDecimal

interface RankingPotencia {
    val nome: String
    val ceg: String
    val potencia: BigDecimal
}

@Repository
interface UsinaRepository : JpaRepository<Usina, String> {
    @Query("SELECT u.ceg FROM Usina u WHERE u.ceg IN :cegs")
    fun findAllCegByCegIn(cegs: Set<String>): List<String>

    @Query("""
        SELECT u.nome as nome, u.ceg as ceg, MAX(r.potenciaOutorgada) as potencia
        FROM Usina u
        JOIN ralie_publicacoes r ON u.ceg = r.usina.ceg
        GROUP BY u.nome, u.ceg
        ORDER BY potencia DESC
        LIMIT 5
    """)
    fun findTop5UsinasMaisPotentes(): List<RankingPotencia>
}