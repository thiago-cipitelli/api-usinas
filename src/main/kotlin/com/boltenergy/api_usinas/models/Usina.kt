package com.boltenergy.api_usinas.models

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import java.math.BigDecimal

@Entity
@Table(name = "usinas")
class Usina(
    @Id
    @Column(length = 50)
    val ceg: String,

    @Column(length = 255)
    val nome: String? = null,

    @Column(name = "tipo_geracao", length = 50)
    val tipoGeracao: String? = null,

    @Column(length = 2)
    val uf: String? = null,

    @OneToMany(mappedBy = "usina", cascade = [CascadeType.ALL], orphanRemoval = true)
    val raliePublicacoes: List<RaliePublicacao> = emptyList()
)
