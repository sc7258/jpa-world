package com.sc7258.jpaworld.service

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("test")
class CountryLanguageQueryServiceIntegrationTest {

    @Autowired
    private lateinit var countryLanguageQueryService: CountryLanguageQueryService

    @Test
    fun `searchByLanguagePattern should return languages matching the pattern`() {
        // When
        val result = countryLanguageQueryService.searchByLanguagePattern("Eng")

        // Then
        assertTrue(result.isNotEmpty())
        assertTrue(result.all { it.language.contains("Eng") })
        
        // Verify some expected languages are in the result
        val languages = result.map { it.language }
        assertTrue(languages.contains("English"))
    }

    @Test
    fun `searchByOfficialAndPercentage should return languages with matching criteria`() {
        // When
        val result = countryLanguageQueryService.searchByOfficialAndPercentage("T", 90.0f)

        // Then
        assertTrue(result.isNotEmpty())
        assertTrue(result.all { it.isOfficial == "T" && it.percentage > 90.0f })
        
        // Verify some expected languages are in the result
        val languageEntries = result.map { "${it.countryCode}-${it.language}" }
        assertTrue(languageEntries.contains("KOR-Korean"))
        assertTrue(languageEntries.contains("JPN-Japanese"))
    }

    @Test
    fun `searchByCountryAndLanguage should return languages for the given country matching the pattern`() {
        // When
        val result = countryLanguageQueryService.searchByCountryAndLanguage("GBR", "Eng")

        // Then
        assertTrue(result.isNotEmpty())
        assertTrue(result.all { it.countryCode == "GBR" && it.language.contains("Eng") })
        
        // Verify the expected language is in the result
        val language = result.find { it.language == "English" }
        assertTrue(language != null)
        assertEquals("GBR", language?.countryCode)
    }

    @Test
    fun `searchByLanguagePattern should return empty list when no languages match the pattern`() {
        // When
        val result = countryLanguageQueryService.searchByLanguagePattern("NonExistentLanguage")

        // Then
        assertTrue(result.isEmpty())
    }

    @Test
    fun `searchByOfficialAndPercentage should return empty list when no languages match the criteria`() {
        // When
        val result = countryLanguageQueryService.searchByOfficialAndPercentage("T", 100.0f) // No language has 100% exactly

        // Then
        assertTrue(result.isEmpty())
    }

    @Test
    fun `searchByCountryAndLanguage should return empty list when no languages match the criteria`() {
        // When
        val result = countryLanguageQueryService.searchByCountryAndLanguage("USA", "NonExistentLanguage")

        // Then
        assertTrue(result.isEmpty())
    }
}