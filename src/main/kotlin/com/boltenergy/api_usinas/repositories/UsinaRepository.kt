package com.boltenergy.api_usinas.repositories

import com.boltenergy.api_usinas.models.Usina
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UsinaRepository : JpaRepository<Usina, Long>