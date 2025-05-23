package com.sc7258.jpaworld.controller

import com.sc7258.jpaworld.entity.CountryLanguage
import com.sc7258.jpaworld.entity.CountryLanguageId
import com.sc7258.jpaworld.repository.CountryLanguageRepository
import com.sc7258.jpaworld.repository.CountryRepository
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/languages")
@Tag(name = "Country Language", description = "Country Language API")
class CountryLanguageController(
    private val countryLanguageRepository: CountryLanguageRepository,
    private val countryRepository: CountryRepository
) {

    @GetMapping
    @Operation(summary = "Get all languages", description = "Retrieves a list of all country languages")
    fun findAll(): List<CountryLanguage> = countryLanguageRepository.findAll()

    @GetMapping("/search/country")
    @Operation(summary = "Search languages by country code", description = "Retrieves languages by country code")
    fun findByCountry(@RequestParam countryCode: String): ResponseEntity<List<CountryLanguage>> {
        val country = countryRepository.findById(countryCode)
        return if (country.isPresent) {
            ResponseEntity.ok(countryLanguageRepository.findByCountry(country.get()))
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @GetMapping("/search/language")
    @Operation(summary = "Search by language", description = "Retrieves countries by language")
    fun findByLanguage(@RequestParam language: String): List<CountryLanguage> =
        countryLanguageRepository.findByLanguage(language)

    @GetMapping("/search/official")
    @Operation(summary = "Search by official status", description = "Retrieves languages by official status (T/F)")
    fun findByIsOfficial(@RequestParam isOfficial: String): List<CountryLanguage> =
        countryLanguageRepository.findByIsOfficial(isOfficial)

    @GetMapping("/search/percentage")
    @Operation(summary = "Search by minimum percentage", description = "Retrieves languages with percentage greater than the specified value")
    fun findByPercentageGreaterThan(@RequestParam minPercentage: Float): List<CountryLanguage> =
        countryLanguageRepository.findByPercentageGreaterThan(minPercentage)
}
