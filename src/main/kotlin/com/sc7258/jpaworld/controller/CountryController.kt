package com.sc7258.jpaworld.controller

import com.sc7258.jpaworld.entity.Country
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
@RequestMapping("/countries")
@Tag(name = "Country", description = "Country API")
class CountryController(private val countryRepository: CountryRepository) {

    @GetMapping
    @Operation(summary = "Get all countries", description = "Retrieves a list of all countries")
    fun findAll(): List<Country> = countryRepository.findAll()

    @GetMapping("/{code}")
    @Operation(summary = "Get country by code", description = "Retrieves a country by its code")
    fun findByCode(@PathVariable code: String): ResponseEntity<Country> =
        countryRepository.findById(code)
            .map { ResponseEntity.ok(it) }
            .orElse(ResponseEntity.notFound().build())

    @GetMapping("/search/name")
    @Operation(summary = "Search countries by name", description = "Retrieves countries by name")
    fun findByName(@RequestParam name: String): List<Country> =
        countryRepository.findByName(name)

    @GetMapping("/search/continent")
    @Operation(summary = "Search countries by continent", description = "Retrieves countries by continent")
    fun findByContinent(@RequestParam continent: String): List<Country> =
        countryRepository.findByContinent(continent)

    @GetMapping("/search/population")
    @Operation(summary = "Search countries by minimum population", description = "Retrieves countries with population greater than the specified value")
    fun findByPopulationGreaterThan(@RequestParam minPopulation: Int): List<Country> =
        countryRepository.findByPopulationGreaterThan(minPopulation)
}
