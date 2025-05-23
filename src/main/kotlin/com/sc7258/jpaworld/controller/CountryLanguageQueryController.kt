package com.sc7258.jpaworld.controller

import com.sc7258.jpaworld.service.CountryLanguageDto
import com.sc7258.jpaworld.service.CountryLanguageQueryService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/query/languages")
@Tag(name = "Country Language Query", description = "Advanced Country Language Query API using jOOQ")
class CountryLanguageQueryController(private val countryLanguageQueryService: CountryLanguageQueryService) {

    @GetMapping("/search/language-pattern")
    @Operation(summary = "Search languages by pattern", description = "Advanced search for languages matching the specified pattern using jOOQ")
    fun searchByLanguagePattern(@RequestParam pattern: String): List<CountryLanguageDto> =
        countryLanguageQueryService.searchByLanguagePattern(pattern)

    @GetMapping("/search/official-percentage")
    @Operation(summary = "Search by official status and minimum percentage", description = "Advanced search for languages with the specified official status and percentage greater than the specified value using jOOQ")
    fun searchByOfficialAndPercentage(
        @RequestParam isOfficial: String,
        @RequestParam minPercentage: Float
    ): List<CountryLanguageDto> =
        countryLanguageQueryService.searchByOfficialAndPercentage(isOfficial, minPercentage)

    @GetMapping("/search/country-language")
    @Operation(summary = "Search by country and language pattern", description = "Advanced search for languages in the specified country matching the specified language pattern using jOOQ")
    fun searchByCountryAndLanguage(
        @RequestParam countryCode: String,
        @RequestParam language: String
    ): List<CountryLanguageDto> =
        countryLanguageQueryService.searchByCountryAndLanguage(countryCode, language)
}
