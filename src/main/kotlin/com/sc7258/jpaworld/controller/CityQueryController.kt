package com.sc7258.jpaworld.controller

import com.sc7258.jpaworld.service.CityDto
import com.sc7258.jpaworld.service.CityQueryService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/query/cities")
@Tag(name = "City Query", description = "Advanced City Query API using jOOQ")
class CityQueryController(private val cityQueryService: CityQueryService) {

    @GetMapping("/search/population")
    @Operation(summary = "Search cities by minimum population", description = "Advanced search for cities with population greater than the specified value using jOOQ")
    fun searchByPopulation(@RequestParam minPopulation: Int): List<CityDto> =
        cityQueryService.searchByPopulation(minPopulation)

    @GetMapping("/search/name-country")
    @Operation(summary = "Search cities by name and country", description = "Advanced search for cities by name pattern and country code using jOOQ")
    fun searchByNameAndCountry(
        @RequestParam name: String,
        @RequestParam countryCode: String
    ): List<CityDto> =
        cityQueryService.searchByNameAndCountry(name, countryCode)
}
