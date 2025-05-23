package com.sc7258.jpaworld.service

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("test")
class CountryQueryServiceIntegrationTest {

    @Autowired
    private lateinit var countryQueryService: CountryQueryService

    @Test
    fun `searchByPopulationAndContinent should return countries with matching criteria`() {
        // When
        val result = countryQueryService.searchByPopulationAndContinent(1000, "Asia")

        // Debug
        println("[DEBUG_LOG] searchByPopulationAndContinent result size: ${result.size}")
        result.forEach { println("[DEBUG_LOG] Country: ${it.name}, Population: ${it.population}, Continent: ${it.continent}") }

        // Then
        assertTrue(result.isNotEmpty())
        assertTrue(result.all { it.population > 1000 && it.continent == "Asia" })

        // Verify some expected countries are in the result
        val countryNames = result.map { it.name }
        assertTrue(countryNames.contains("Japan") || countryNames.contains("South Korea"))
    }

    @Test
    fun `searchByLifeExpectancy should return countries with life expectancy greater than the given value`() {
        // When
        val result = countryQueryService.searchByLifeExpectancy(70.0f)

        // Debug
        println("[DEBUG_LOG] searchByLifeExpectancy result size: ${result.size}")
        result.forEach { println("[DEBUG_LOG] Country: ${it.name}, Life Expectancy: ${it.lifeExpectancy}") }

        // Then
        assertTrue(result.isNotEmpty())
        assertTrue(result.all { it.lifeExpectancy != null && it.lifeExpectancy!!.compareTo(70.0f) > 0 })

        // Verify some expected countries are in the result
        val countryNames = result.map { it.name }
        assertTrue(countryNames.contains("Japan") || countryNames.contains("France") || 
                  countryNames.contains("United Kingdom") || countryNames.contains("South Korea"))
    }

    @Test
    fun `searchByGnpRange should return countries with GNP in the given range`() {
        // When
        val result = countryQueryService.searchByGnpRange(100000.0f, 9000000.0f)

        // Debug
        println("[DEBUG_LOG] searchByGnpRange result size: ${result.size}")
        result.forEach { println("[DEBUG_LOG] Country: ${it.name}, GNP: ${it.gnp}") }

        // Then
        assertTrue(result.isNotEmpty())
        assertTrue(result.all { it.gnp != null && it.gnp!!.compareTo(100000.0f) >= 0 && it.gnp!!.compareTo(9000000.0f) <= 0 })

        // Verify some expected countries are in the result
        val countryNames = result.map { it.name }
        assertTrue(countryNames.contains("United Kingdom") || countryNames.contains("France") || 
                  countryNames.contains("Japan") || countryNames.contains("South Korea"))
    }

    @Test
    fun `searchByPopulationAndContinent should return empty list when no countries match the criteria`() {
        // When
        val result = countryQueryService.searchByPopulationAndContinent(1000000000, "Asia") // Very high population threshold

        // Then
        assertTrue(result.isEmpty())
    }

    @Test
    fun `searchByLifeExpectancy should return empty list when no countries match the criteria`() {
        // When
        val result = countryQueryService.searchByLifeExpectancy(100.0f) // Very high life expectancy threshold

        // Then
        assertTrue(result.isEmpty())
    }

    @Test
    fun `searchByGnpRange should return empty list when no countries match the criteria`() {
        // When
        val result = countryQueryService.searchByGnpRange(10000000.0f, 20000000.0f) // Very high GNP range

        // Then
        assertTrue(result.isEmpty())
    }
}
