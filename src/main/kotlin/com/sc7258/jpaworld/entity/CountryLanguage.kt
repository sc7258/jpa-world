package com.sc7258.jpaworld.entity

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.IdClass
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.io.Serializable

@Entity
@Table(name = "countrylanguage")
@IdClass(CountryLanguageId::class)
data class CountryLanguage(
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_code", referencedColumnName = "code", nullable = false)
    @JsonIgnoreProperties("cities", "languages")
    val country: Country,

    @Id
    @Column(name = "language", length = 30, nullable = false)
    val language: String,

    @Column(name = "is_official", nullable = false)
    val isOfficial: String,

    @Column(name = "percentage", nullable = false)
    val percentage: Float
)

data class CountryLanguageId(
    val country: String = "",
    val language: String = ""
) : Serializable
