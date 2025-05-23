package com.sc7258.jpaworld.service

import org.jooq.DSLContext
import org.springframework.stereotype.Service

@Service
class CityQueryService(private val dsl: DSLContext) {

    fun searchByPopulation(minPop: Int): List<CityDto> {
        return dsl.selectFrom("city")
            .where("population > ?", minPop)
            .fetchInto(CityDto::class.java)
    }

    fun searchByNameAndCountry(name: String, countryCode: String): List<CityDto> {
        return dsl.selectFrom("city")
            .where("name like ? and country_code = ?", "%$name%", countryCode)
            .fetchInto(CityDto::class.java)
    }
}

data class CityDto(
    val id: Long,
    val name: String,
    val countryCode: String,
    val district: String,
    val population: Int
)