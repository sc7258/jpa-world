package com.sc7258.jpaworld.repository

import com.sc7258.jpaworld.entity.CountryLanguageId
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("test")
class CountryLanguageRepositoryIntegrationTest {

    @Autowired
    private lateinit var countryLanguageRepository: CountryLanguageRepository

    @Autowired
    private lateinit var countryRepository: CountryRepository

    @Test
    fun `findByLanguage should return entries with matching language`() {
        // When
        val result = countryLanguageRepository.findByLanguage("English")

        // Then
        assertTrue(result.isNotEmpty())
        assertTrue(result.all { it.language == "English" })
        
        // Verify some expected country codes are in the result
        val countryCodes = result.map { it.country.code }
        assertTrue(countryCodes.contains("USA"))
        assertTrue(countryCodes.contains("GBR"))
    }

    @Test
    fun `findByCountry should return languages for the given country`() {
        // Given
        val country = countryRepository.findById("USA").orElseThrow()

        // When
        val result = countryLanguageRepository.findByCountry(country)

        // Then
        assertTrue(result.isNotEmpty())
        assertTrue(result.all { it.country.code == "USA" })
        
        // Verify some expected languages are in the result
        val languages = result.map { it.language }
        assertTrue(languages.contains("English"))
        assertTrue(languages.contains("Spanish"))
    }

    @Test
    fun `findByIsOfficial should return languages with matching official status`() {
        // When
        val result = countryLanguageRepository.findByIsOfficial("T")

        // Then
        assertTrue(result.isNotEmpty())
        assertTrue(result.all { it.isOfficial == "T" })
        
        // Verify some expected entries are in the result
        val entries = result.map { "${it.country.code}-${it.language}" }
        assertTrue(entries.contains("USA-English"))
        assertTrue(entries.contains("KOR-Korean"))
        assertTrue(entries.contains("JPN-Japanese"))
    }

    @Test
    fun `findByPercentageGreaterThan should return languages with percentage greater than the given value`() {
        // When
        val result = countryLanguageRepository.findByPercentageGreaterThan(90.0f)

        // Then
        assertTrue(result.isNotEmpty())
        assertTrue(result.all { it.percentage > 90.0f })
        
        // Verify some expected entries are in the result
        val entries = result.map { "${it.country.code}-${it.language}" }
        assertTrue(entries.contains("KOR-Korean"))
        assertTrue(entries.contains("JPN-Japanese"))
    }

    @Test
    fun `findById should return the language entry with the given composite key`() {
        // Given
        val id = CountryLanguageId(country = "USA", language = "English")

        // When
        val result = countryLanguageRepository.findById(id)

        // Then
        assertTrue(result.isPresent)
        assertEquals("USA", result.get().country.code)
        assertEquals("English", result.get().language)
    }

    @Test
    fun `findByLanguage should return empty list when no entries match the language`() {
        // When
        val result = countryLanguageRepository.findByLanguage("NonExistentLanguage")

        // Then
        assertTrue(result.isEmpty())
    }

    @Test
    fun `findByIsOfficial should return empty list when no entries match the official status`() {
        // When
        val result = countryLanguageRepository.findByIsOfficial("X") // Invalid official status

        // Then
        assertTrue(result.isEmpty())
    }

    @Test
    fun `findByPercentageGreaterThan should return empty list when no entries match the percentage threshold`() {
        // When
        val result = countryLanguageRepository.findByPercentageGreaterThan(100.0f) // No language has 100% exactly

        // Then
        assertTrue(result.isEmpty())
    }

    @Test
    fun `findById should return empty optional when no entry matches the composite key`() {
        // Given
        val id = CountryLanguageId(country = "USA", language = "NonExistentLanguage")

        // When
        val result = countryLanguageRepository.findById(id)

        // Then
        assertTrue(result.isEmpty)
    }
}