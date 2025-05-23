package com.sc7258.jpaworld.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table

@Entity
@Table(name = "country")
data class Country(
    @Id
    @Column(name = "code", length = 3)
    val code: String,

    @Column(name = "name", length = 52, nullable = false)
    val name: String,

    @Column(name = "continent", length = 50, nullable = false)
    val continent: String,

    @Column(name = "region", length = 26, nullable = false)
    val region: String,

    @Column(name = "surface_area", nullable = false)
    val surfaceArea: Float,

    @Column(name = "indep_year")
    val indepYear: Int?,

    @Column(name = "population", nullable = false)
    val population: Int,

    @Column(name = "life_expectancy")
    val lifeExpectancy: Float?,

    @Column(name = "gnp")
    val gnp: Float?,

    @Column(name = "gnp_old")
    val gnpOld: Float?,

    @Column(name = "local_name", length = 45, nullable = false)
    val localName: String,

    @Column(name = "government_form", length = 45, nullable = false)
    val governmentForm: String,

    @Column(name = "head_of_state", length = 60)
    val headOfState: String?,

    @Column(name = "capital")
    val capital: Int?,

    @Column(name = "code2", length = 2, nullable = false)
    val code2: String
) {
    @JsonIgnore
    @OneToMany(mappedBy = "country")
    lateinit var cities: List<City>

    @JsonIgnore
    @OneToMany(mappedBy = "country")
    lateinit var languages: List<CountryLanguage>
}
