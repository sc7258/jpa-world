package com.sc7258.jpaworld.service

import org.jooq.DSLContext
import org.springframework.stereotype.Service

@Service
class CountryQueryService(private val dsl: DSLContext) {

    fun searchByPopulationAndContinent(minPop: Int, continent: String): List<CountryDto> {
        return dsl.selectFrom("country")
            .where("population > ? and continent = ?", minPop, continent)
            .fetchInto(CountryDto::class.java)
    }

    fun searchByLifeExpectancy(minLifeExpectancy: Float): List<CountryDto> {
        return dsl.selectFrom("country")
            .where("life_expectancy > ?", minLifeExpectancy)
            .fetchInto(CountryDto::class.java)
    }

    fun searchByGnpRange(minGnp: Float, maxGnp: Float): List<CountryDto> {
        return dsl.selectFrom("country")
            .where("gnp between ? and ?", minGnp, maxGnp)
            .fetchInto(CountryDto::class.java)
    }
}

data class CountryDto(
    val code: String,
    val name: String,
    val continent: String,
    val region: String,
    val surfaceArea: Float,
    val indepYear: Int?,
    val population: Int,
    val lifeExpectancy: Float?,
    val gnp: Float?,
    val gnpOld: Float?,
    val localName: String,
    val governmentForm: String,
    val headOfState: String?,
    val capital: Int?,
    val code2: String
)