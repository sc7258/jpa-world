package com.sc7258.jpaworld.controller

import com.sc7258.jpaworld.entity.City
import com.sc7258.jpaworld.repository.CityRepository
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
@RequestMapping("/cities")
@Tag(name = "City", description = "City API")
class CityController(
    private val cityRepository: CityRepository,
    private val countryRepository: CountryRepository
) {

    @GetMapping
    @Operation(summary = "Get all cities", description = "Retrieves a list of all cities")
    fun findAll(): List<City> = cityRepository.findAll()

    @GetMapping("/{id}")
    @Operation(summary = "Get city by ID", description = "Retrieves a city by its ID")
    fun findById(@PathVariable id: Long): ResponseEntity<City> =
        cityRepository.findById(id)
            .map { ResponseEntity.ok(it) }
            .orElse(ResponseEntity.notFound().build())

    @GetMapping("/search/name")
    @Operation(summary = "Search cities by name", description = "Retrieves cities by name")
    fun findByName(@RequestParam name: String): List<City> =
        cityRepository.findByName(name)

    @GetMapping("/search/country")
    @Operation(summary = "Search cities by country code", description = "Retrieves cities by country code")
    fun findByCountry(@RequestParam countryCode: String): ResponseEntity<List<City>> {
        val country = countryRepository.findById(countryCode)
        return if (country.isPresent) {
            ResponseEntity.ok(cityRepository.findByCountry(country.get()))
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @GetMapping("/search/district")
    @Operation(summary = "Search cities by district", description = "Retrieves cities by district")
    fun findByDistrict(@RequestParam district: String): List<City> =
        cityRepository.findByDistrict(district)

    @GetMapping("/search/population")
    @Operation(summary = "Search cities by minimum population", description = "Retrieves cities with population greater than the specified value")
    fun findByPopulationGreaterThan(@RequestParam minPopulation: Int): List<City> =
        cityRepository.findByPopulationGreaterThan(minPopulation)
}
