package com.sc7258.jpaworld.service

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("test")
class CityQueryServiceIntegrationTest {

    @Autowired
    private lateinit var cityQueryService: CityQueryService

    @Test
    fun `searchByPopulation should return cities with population greater than the given value`() {
        // When
        val result = cityQueryService.searchByPopulation(5000000)

        // Then
        assertTrue(result.isNotEmpty())
        assertTrue(result.all { it.population > 5000000 })
        
        // Verify some expected cities are in the result
        val cityNames = result.map { it.name }
        assertTrue(cityNames.contains("New York"))
        assertTrue(cityNames.contains("Seoul"))
        assertTrue(cityNames.contains("Tokyo"))
    }

    @Test
    fun `searchByNameAndCountry should return cities matching name pattern and country code`() {
        // When
        val result = cityQueryService.searchByNameAndCountry("New", "USA")

        // Then
        assertTrue(result.isNotEmpty())
        assertTrue(result.all { it.name.contains("New") && it.countryCode == "USA" })
        
        // Verify the expected city is in the result
        val city = result.find { it.name == "New York" }
        assertTrue(city != null)
        assertEquals("USA", city?.countryCode)
    }

    @Test
    fun `searchByPopulation should return empty list when no cities match the criteria`() {
        // When
        val result = cityQueryService.searchByPopulation(100000000) // Very high population threshold

        // Then
        assertTrue(result.isEmpty())
    }

    @Test
    fun `searchByNameAndCountry should return empty list when no cities match the criteria`() {
        // When
        val result = cityQueryService.searchByNameAndCountry("NonExistentCity", "USA")

        // Then
        assertTrue(result.isEmpty())
    }
}