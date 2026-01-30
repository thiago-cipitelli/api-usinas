package com.boltenergy.api_usinas.models

import com.boltenergy.api_usinas.models.Usina
import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDate

@Entity
@Table(
    name = "ralie_publicacoes",
    uniqueConstraints = [UniqueConstraint(columnNames = ["ceg", "data_publicacao"])]
)
class RaliePublicacao(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ceg", nullable = false)
    val usina: Usina,

    @Column(name = "data_publicacao", nullable = false)
    val dataPublicacao: LocalDate,

    @Column(name = "potencia_outorgada", precision = 15, scale = 2)
    val potenciaOutorgada: BigDecimal? = null
    )
