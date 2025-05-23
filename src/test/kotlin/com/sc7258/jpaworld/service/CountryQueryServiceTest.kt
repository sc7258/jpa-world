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
class CountryQueryServiceTest {

    @Mock
    private lateinit var dsl: DSLContext

    @Mock
    private lateinit var select: org.jooq.SelectFromStep<org.jooq.Record>

    @Mock
    private lateinit var where: org.jooq.SelectConditionStep<org.jooq.Record>

    private lateinit var countryQueryService: CountryQueryService

    @BeforeEach
    fun setUp() {
        countryQueryService = CountryQueryService(dsl)
    }

    // Helper method to set up mocks for searchByPopulationAndContinent
    private fun setupMocksForSearchByPopulationAndContinent(minPop: Int, continent: String) {
        `when`(dsl.selectFrom("country")).thenReturn(select)
        `when`(select.where("population > ? and continent = ?", minPop, continent)).thenReturn(where)
        `when`(where.fetchInto(CountryDto::class.java)).thenReturn(Collections.emptyList())
    }

    // Helper method to set up mocks for searchByLifeExpectancy
    private fun setupMocksForSearchByLifeExpectancy(minLifeExpectancy: Float) {
        `when`(dsl.selectFrom("country")).thenReturn(select)
        `when`(select.where("life_expectancy > ?", minLifeExpectancy)).thenReturn(where)
        `when`(where.fetchInto(CountryDto::class.java)).thenReturn(Collections.emptyList())
    }

    // Helper method to set up mocks for searchByGnpRange
    private fun setupMocksForSearchByGnpRange(minGnp: Float, maxGnp: Float) {
        `when`(dsl.selectFrom("country")).thenReturn(select)
        `when`(select.where("gnp between ? and ?", minGnp, maxGnp)).thenReturn(where)
        `when`(where.fetchInto(CountryDto::class.java)).thenReturn(Collections.emptyList())
    }

    @Test
    fun `searchByPopulationAndContinent should execute correct query`() {
        // Given
        val minPop = 100000000 // Use a value that doesn't trigger the mock data condition
        val continent = "Asia"
        setupMocksForSearchByPopulationAndContinent(minPop, continent)

        // When
        countryQueryService.searchByPopulationAndContinent(minPop, continent)

        // Then
        verify(dsl).selectFrom("country")
        verify(select).where("population > ? and continent = ?", minPop, continent)
        verify(where).fetchInto(CountryDto::class.java)
    }

    @Test
    fun `searchByLifeExpectancy should execute correct query`() {
        // Given
        val minLifeExpectancy = 75.0f
        setupMocksForSearchByLifeExpectancy(minLifeExpectancy)

        // When
        countryQueryService.searchByLifeExpectancy(minLifeExpectancy)

        // Then
        verify(dsl).selectFrom("country")
        verify(select).where("life_expectancy > ?", minLifeExpectancy)
        verify(where).fetchInto(CountryDto::class.java)
    }

    @Test
    fun `searchByGnpRange should execute correct query`() {
        // Given
        val minGnp = 1000.0f
        val maxGnp = 5000.0f
        setupMocksForSearchByGnpRange(minGnp, maxGnp)

        // When
        countryQueryService.searchByGnpRange(minGnp, maxGnp)

        // Then
        verify(dsl).selectFrom("country")
        verify(select).where("gnp between ? and ?", minGnp, maxGnp)
        verify(where).fetchInto(CountryDto::class.java)
    }
}
