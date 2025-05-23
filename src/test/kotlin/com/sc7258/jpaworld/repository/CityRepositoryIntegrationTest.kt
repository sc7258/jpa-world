package com.sc7258.jpaworld.repository

import com.sc7258.jpaworld.entity.Country
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("test")
class CityRepositoryIntegrationTest {

    @Autowired
    private lateinit var cityRepository: CityRepository

    @Autowired
    private lateinit var countryRepository: CountryRepository

    @Test
    fun `findByName should return cities with matching name`() {
        // When
        val result = cityRepository.findByName("New York")

        // Then
        assertTrue(result.isNotEmpty())
        assertEquals("New York", result[0].name)
    }

    @Test
    fun `findByCountry should return cities in the given country`() {
        // Given
        val country = countryRepository.findById("USA").orElseThrow()

        // When
        val result = cityRepository.findByCountry(country)

        // Then
        assertTrue(result.isNotEmpty())
        assertTrue(result.all { it.country.code == "USA" })
        
        // Verify some expected cities are in the result
        val cityNames = result.map { it.name }
        assertTrue(cityNames.contains("New York"))
        assertTrue(cityNames.contains("Los Angeles"))
        assertTrue(cityNames.contains("Chicago"))
    }

    @Test
    fun `findByDistrict should return cities in the given district`() {
        // When
        val result = cityRepository.findByDistrict("New York")

        // Then
        assertTrue(result.isNotEmpty())
        assertTrue(result.all { it.district == "New York" })
        
        // Verify the expected city is in the result
        val cityNames = result.map { it.name }
        assertTrue(cityNames.contains("New York"))
    }

    @Test
    fun `findByPopulationGreaterThan should return cities with population greater than the given value`() {
        // When
        val result = cityRepository.findByPopulationGreaterThan(8000000)

        // Then
        assertTrue(result.isNotEmpty())
        assertTrue(result.all { it.population > 8000000 })
        
        // Verify some expected cities are in the result
        val cityNames = result.map { it.name }
        assertTrue(cityNames.contains("Seoul"))
    }

    @Test
    fun `findByName should return empty list when no cities match the name`() {
        // When
        val result = cityRepository.findByName("NonExistentCity")

        // Then
        assertTrue(result.isEmpty())
    }

    @Test
    fun `findByDistrict should return empty list when no cities match the district`() {
        // When
        val result = cityRepository.findByDistrict("NonExistentDistrict")

        // Then
        assertTrue(result.isEmpty())
    }

    @Test
    fun `findByPopulationGreaterThan should return empty list when no cities match the population threshold`() {
        // When
        val result = cityRepository.findByPopulationGreaterThan(100000000) // Very high population threshold

        // Then
        assertTrue(result.isEmpty())
    }
}