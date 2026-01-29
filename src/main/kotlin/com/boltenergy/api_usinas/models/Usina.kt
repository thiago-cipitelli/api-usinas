package com.boltenergy.api_usinas.models

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "usinas")
class Usina(

    @Id
    @Column(name = "id", nullable = false)
    val id: Long,

    @Column(name = "uf", nullable = false, length = 2)
    val uf: String,

    @Column(name = "origem_combustivel", nullable = false)
    val origemCombustivel: String,

    @Column(name = "potencia_kw", nullable = false)
    val potenciaKw: Double,

    @Column(name = "nome_empreendimento", nullable = false)
    val nomeEmpreendimento: String
)
