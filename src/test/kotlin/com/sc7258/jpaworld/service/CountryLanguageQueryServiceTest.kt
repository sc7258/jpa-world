package com.sc7258.jpaworld.service

import org.jooq.DSLContext
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import java.util.Collections

@ExtendWith(MockitoExtension::class)
class CountryLanguageQueryServiceTest {

    @Mock
    private lateinit var dsl: DSLContext

    @Mock
    private lateinit var select: org.jooq.SelectFromStep<org.jooq.Record>

    @Mock
    private lateinit var where: org.jooq.SelectConditionStep<org.jooq.Record>

    private lateinit var countryLanguageQueryService: CountryLanguageQueryService

    @BeforeEach
    fun setUp() {
        countryLanguageQueryService = CountryLanguageQueryService(dsl)
    }

    // Helper method to set up mocks for searchByLanguagePattern
    private fun setupMocksForSearchByLanguagePattern(pattern: String) {
        `when`(dsl.selectFrom("countrylanguage")).thenReturn(select)
        `when`(select.where("language like ?", "%$pattern%")).thenReturn(where)
        `when`(where.fetchInto(CountryLanguageDto::class.java)).thenReturn(Collections.emptyList())
    }

    // Helper method to set up mocks for searchByOfficialAndPercentage
    private fun setupMocksForSearchByOfficialAndPercentage(isOfficial: String, minPercentage: Float) {
        `when`(dsl.selectFrom("countrylanguage")).thenReturn(select)
        `when`(select.where("is_official = ? and percentage > ?", isOfficial, minPercentage)).thenReturn(where)
        `when`(where.fetchInto(CountryLanguageDto::class.java)).thenReturn(Collections.emptyList())
    }

    // Helper method to set up mocks for searchByCountryAndLanguage
    private fun setupMocksForSearchByCountryAndLanguage(countryCode: String, language: String) {
        `when`(dsl.selectFrom("countrylanguage")).thenReturn(select)
        `when`(select.where("country_code = ? and language like ?", countryCode, "%$language%")).thenReturn(where)
        `when`(where.fetchInto(CountryLanguageDto::class.java)).thenReturn(Collections.emptyList())
    }

    @Test
    fun `searchByLanguagePattern should execute correct query`() {
        // Given
        val pattern = "Korean"
        setupMocksForSearchByLanguagePattern(pattern)

        // When
        countryLanguageQueryService.searchByLanguagePattern(pattern)

        // Then
        verify(dsl).selectFrom("countrylanguage")
        verify(select).where("language like ?", "%$pattern%")
        verify(where).fetchInto(CountryLanguageDto::class.java)
    }

    @Test
    fun `searchByOfficialAndPercentage should execute correct query`() {
        // Given
        val isOfficial = "T"
        val minPercentage = 50.0f
        setupMocksForSearchByOfficialAndPercentage(isOfficial, minPercentage)

        // When
        countryLanguageQueryService.searchByOfficialAndPercentage(isOfficial, minPercentage)

        // Then
        verify(dsl).selectFrom("countrylanguage")
        verify(select).where("is_official = ? and percentage > ?", isOfficial, minPercentage)
        verify(where).fetchInto(CountryLanguageDto::class.java)
    }

    @Test
    fun `searchByCountryAndLanguage should execute correct query`() {
        // Given
        val countryCode = "KOR"
        val language = "Korean"
        setupMocksForSearchByCountryAndLanguage(countryCode, language)

        // When
        countryLanguageQueryService.searchByCountryAndLanguage(countryCode, language)

        // Then
        verify(dsl).selectFrom("countrylanguage")
        verify(select).where("country_code = ? and language like ?", countryCode, "%$language%")
        verify(where).fetchInto(CountryLanguageDto::class.java)
    }
}
