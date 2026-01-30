package com.boltenergy.api_usinas.repositories

import com.boltenergy.api_usinas.models.Usina
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface UsinaRepository : JpaRepository<Usina, String> {
    @Query("SELECT u.ceg FROM Usina u WHERE u.ceg IN :cegs")
    fun findAllCegByCegIn(cegs: Set<String>): List<String>
}