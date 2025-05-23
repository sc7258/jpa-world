package com.sc7258.jpaworld.repository

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("test")
class CountryRepositoryIntegrationTest {

    @Autowired
    private lateinit var countryRepository: CountryRepository

    @Test
    fun `findByName should return countries with matching name`() {
        // When
        val result = countryRepository.findByName("Japan")

        // Then
        assertTrue(result.isNotEmpty())
        assertEquals("Japan", result[0].name)
        assertEquals("JPN", result[0].code)
    }

    @Test
    fun `findByContinent should return countries in the given continent`() {
        // When
        val result = countryRepository.findByContinent("Asia")

        // Then
        assertTrue(result.isNotEmpty())
        assertTrue(result.all { it.continent == "Asia" })
        
        // Verify some expected countries are in the result
        val countryNames = result.map { it.name }
        assertTrue(countryNames.contains("Japan"))
        assertTrue(countryNames.contains("South Korea"))
    }

    @Test
    fun `findByPopulationGreaterThan should return countries with population greater than the given value`() {
        // When
        val result = countryRepository.findByPopulationGreaterThan(100000000)

        // Then
        assertTrue(result.isNotEmpty())
        assertTrue(result.all { it.population > 100000000 })
        
        // Verify some expected countries are in the result
        val countryNames = result.map { it.name }
        assertTrue(countryNames.contains("Japan"))
    }

    @Test
    fun `findById should return the country with the given code`() {
        // When
        val result = countryRepository.findById("USA")

        // Then
        assertTrue(result.isPresent)
        assertEquals("United States", result.get().name)
    }

    @Test
    fun `findByName should return empty list when no countries match the name`() {
        // When
        val result = countryRepository.findByName("NonExistentCountry")

        // Then
        assertTrue(result.isEmpty())
    }

    @Test
    fun `findByContinent should return empty list when no countries match the continent`() {
        // When
        val result = countryRepository.findByContinent("NonExistentContinent")

        // Then
        assertTrue(result.isEmpty())
    }

    @Test
    fun `findByPopulationGreaterThan should return empty list when no countries match the population threshold`() {
        // When
        val result = countryRepository.findByPopulationGreaterThan(1000000000) // Very high population threshold

        // Then
        assertTrue(result.isEmpty())
    }

    @Test
    fun `findById should return empty optional when no country matches the code`() {
        // When
        val result = countryRepository.findById("XXX")

        // Then
        assertTrue(result.isEmpty)
    }
}