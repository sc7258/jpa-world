package com.sc7258.jpaworld.controller

import com.sc7258.jpaworld.service.CountryDto
import com.sc7258.jpaworld.service.CountryQueryService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/query/countries")
@Tag(name = "Country Query", description = "Advanced Country Query API using jOOQ")
class CountryQueryController(private val countryQueryService: CountryQueryService) {

    @GetMapping("/search/population-continent")
    @Operation(summary = "Search countries by population and continent", description = "Advanced search for countries with population greater than the specified value and in the specified continent using jOOQ")
    fun searchByPopulationAndContinent(
        @RequestParam minPopulation: Int,
        @RequestParam continent: String
    ): List<CountryDto> =
        countryQueryService.searchByPopulationAndContinent(minPopulation, continent)

    @GetMapping("/search/life-expectancy")
    @Operation(summary = "Search countries by minimum life expectancy", description = "Advanced search for countries with life expectancy greater than the specified value using jOOQ")
    fun searchByLifeExpectancy(@RequestParam minLifeExpectancy: Float): List<CountryDto> =
        countryQueryService.searchByLifeExpectancy(minLifeExpectancy)

    @GetMapping("/search/gnp-range")
    @Operation(summary = "Search countries by GNP range", description = "Advanced search for countries with GNP between the specified minimum and maximum values using jOOQ")
    fun searchByGnpRange(
        @RequestParam minGnp: Float,
        @RequestParam maxGnp: Float
    ): List<CountryDto> =
        countryQueryService.searchByGnpRange(minGnp, maxGnp)
}
