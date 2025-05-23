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
class CityQueryServiceTest {

    @Mock
    private lateinit var dsl: DSLContext

    @Mock
    private lateinit var select: org.jooq.SelectFromStep<org.jooq.Record>

    @Mock
    private lateinit var where: org.jooq.SelectConditionStep<org.jooq.Record>

    private lateinit var cityQueryService: CityQueryService

    @BeforeEach
    fun setUp() {
        cityQueryService = CityQueryService(dsl)
    }

    // Helper method to set up mocks for searchByPopulation
    private fun setupMocksForSearchByPopulation(minPop: Int) {
        `when`(dsl.selectFrom("city")).thenReturn(select)
        `when`(select.where("population > ?", minPop)).thenReturn(where)
        `when`(where.fetchInto(CityDto::class.java)).thenReturn(Collections.emptyList())
    }

    // Helper method to set up mocks for searchByNameAndCountry
    private fun setupMocksForSearchByNameAndCountry(name: String, countryCode: String) {
        `when`(dsl.selectFrom("city")).thenReturn(select)
        `when`(select.where("name like ? and country_code = ?", "%$name%", countryCode)).thenReturn(where)
        `when`(where.fetchInto(CityDto::class.java)).thenReturn(Collections.emptyList())
    }

    @Test
    fun `searchByPopulation should execute correct query`() {
        // Given
        val minPop = 1000000
        setupMocksForSearchByPopulation(minPop)

        // When
        cityQueryService.searchByPopulation(minPop)

        // Then
        verify(dsl).selectFrom("city")
        verify(select).where("population > ?", minPop)
        verify(where).fetchInto(CityDto::class.java)
    }

    @Test
    fun `searchByNameAndCountry should execute correct query`() {
        // Given
        val name = "Seoul"
        val countryCode = "KOR"
        setupMocksForSearchByNameAndCountry(name, countryCode)

        // When
        cityQueryService.searchByNameAndCountry(name, countryCode)

        // Then
        verify(dsl).selectFrom("city")
        verify(select).where("name like ? and country_code = ?", "%$name%", countryCode)
        verify(where).fetchInto(CityDto::class.java)
    }
}
