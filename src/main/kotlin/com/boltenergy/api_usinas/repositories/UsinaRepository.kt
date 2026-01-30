package com.boltenergy.api_usinas.repositories

import com.boltenergy.api_usinas.models.Usina
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.jpa.repository.query.DeclaredQuery.nativeQuery
import org.springframework.stereotype.Repository
import java.math.BigDecimal
import java.time.LocalDate

interface RankingPotencia {
    val nome: String
    val ceg: String
    val potencia: BigDecimal
    val dataRalie: LocalDate
}

@Repository
interface UsinaRepository : JpaRepository<Usina, String> {
    @Query("SELECT u.ceg FROM Usina u WHERE u.ceg IN :cegs")
    fun findAllCegByCegIn(cegs: Set<String>): List<String>

    @Query(value = """
       SELECT nome, ceg, potencia, data_ralie
        FROM (
            SELECT 
                u.nome as nome, 
                u.ceg as ceg, 
                r.potencia_outorgada as potencia,
                r.data_publicacao as data_ralie,
                ROW_NUMBER() OVER (PARTITION BY u.ceg ORDER BY r.potencia_outorgada DESC, r.data_publicacao DESC) as rn
            FROM usinas u
            JOIN ralie_publicacoes r ON u.ceg = r.ceg
        ) as sub
        WHERE rn = 1
        ORDER BY potencia DESC
        LIMIT 5
    """, nativeQuery = true)
    fun findTop5UsinasMaisPotentes(): List<RankingPotencia>
}