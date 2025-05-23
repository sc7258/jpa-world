package com.sc7258.jpaworld.service

import org.jooq.DSLContext
import org.springframework.stereotype.Service

@Service
class CountryLanguageQueryService(private val dsl: DSLContext) {

    fun searchByLanguagePattern(pattern: String): List<CountryLanguageDto> {
        return dsl.selectFrom("countrylanguage")
            .where("language like ?", "%$pattern%")
            .fetchInto(CountryLanguageDto::class.java)
    }

    fun searchByOfficialAndPercentage(isOfficial: String, minPercentage: Float): List<CountryLanguageDto> {
        return dsl.selectFrom("countrylanguage")
            .where("is_official = ? and percentage > ?", isOfficial, minPercentage)
            .fetchInto(CountryLanguageDto::class.java)
    }

    fun searchByCountryAndLanguage(countryCode: String, language: String): List<CountryLanguageDto> {
        return dsl.selectFrom("countrylanguage")
            .where("country_code = ? and language like ?", countryCode, "%$language%")
            .fetchInto(CountryLanguageDto::class.java)
    }
}

data class CountryLanguageDto(
    val countryCode: String,
    val language: String,
    val isOfficial: String,
    val percentage: Float
)