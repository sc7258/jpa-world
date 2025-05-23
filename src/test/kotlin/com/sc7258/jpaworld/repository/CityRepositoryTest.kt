package com.sc7258.jpaworld.repository

import com.sc7258.jpaworld.entity.City
import com.sc7258.jpaworld.entity.Country
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.test.context.ActiveProfiles

@DataJpaTest
@ActiveProfiles("unit-test")
class CityRepositoryTest {

    @Autowired
    private lateinit var entityManager: TestEntityManager

    @Autowired
    private lateinit var cityRepository: CityRepository

    private lateinit var country: Country
    private lateinit var city1: City
    private lateinit var city2: City

    @BeforeEach
    fun setUp() {
        // Create test country
        country = Country(
            code = "TST",
            name = "Test Country",
            continent = "Test Continent",
            region = "Test Region",
            surfaceArea = 1000.0f,
            indepYear = 2000,
            population = 1000000,
            lifeExpectancy = 75.0f,
            gnp = 1000.0f,
            gnpOld = 900.0f,
            localName = "Test Local Name",
            governmentForm = "Test Government",
            headOfState = "Test Head",
            capital = 1,
            code2 = "TS"
        )
        entityManager.persist(country)

        // Create test cities
        city1 = City(
            name = "Test City 1",
            country = country,
            district = "Test District 1",
            population = 500000
        )
        entityManager.persist(city1)

        city2 = City(
            name = "Test City 2",
            country = country,
            district = "Test District 2",
            population = 1500000
        )
        entityManager.persist(city2)

        entityManager.flush()
    }

    @Test
    fun `findByName should return cities with matching name`() {
        // When
        val result = cityRepository.findByName("Test City 1")

        // Then
        assertEquals(1, result.size)
        assertEquals("Test City 1", result[0].name)
    }

    @Test
    fun `findByCountry should return cities in the given country`() {
        // When
        val result = cityRepository.findByCountry(country)

        // Then
        assertEquals(2, result.size)
        assertEquals("TST", result[0].country.code)
        assertEquals("TST", result[1].country.code)
    }

    @Test
    fun `findByDistrict should return cities in the given district`() {
        // When
        val result = cityRepository.findByDistrict("Test District 1")

        // Then
        assertEquals(1, result.size)
        assertEquals("Test District 1", result[0].district)
    }

    @Test
    fun `findByPopulationGreaterThan should return cities with population greater than the given value`() {
        // When
        val result = cityRepository.findByPopulationGreaterThan(1000000)

        // Then
        assertEquals(1, result.size)
        assertEquals("Test City 2", result[0].name)
        assertEquals(1500000, result[0].population)
    }
}
