package com.sc7258.jpaworld.repository

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
class CountryRepositoryTest {

    @Autowired
    private lateinit var entityManager: TestEntityManager

    @Autowired
    private lateinit var countryRepository: CountryRepository

    private lateinit var country1: Country
    private lateinit var country2: Country
    private lateinit var country3: Country

    @BeforeEach
    fun setUp() {
        // Create test countries
        country1 = Country(
            code = "TS1",
            name = "Test Country 1",
            continent = "Asia",
            region = "East Asia",
            surfaceArea = 1000.0f,
            indepYear = 1950,
            population = 5000000,
            lifeExpectancy = 75.0f,
            gnp = 1000.0f,
            gnpOld = 900.0f,
            localName = "Test Local Name 1",
            governmentForm = "Republic",
            headOfState = "Test Head 1",
            capital = 1,
            code2 = "T1"
        )
        entityManager.persist(country1)

        country2 = Country(
            code = "TS2",
            name = "Test Country 2",
            continent = "Europe",
            region = "Western Europe",
            surfaceArea = 2000.0f,
            indepYear = 1800,
            population = 10000000,
            lifeExpectancy = 80.0f,
            gnp = 2000.0f,
            gnpOld = 1800.0f,
            localName = "Test Local Name 2",
            governmentForm = "Monarchy",
            headOfState = "Test Head 2",
            capital = 2,
            code2 = "T2"
        )
        entityManager.persist(country2)

        country3 = Country(
            code = "TS3",
            name = "Test Country 3",
            continent = "Asia",
            region = "South Asia",
            surfaceArea = 3000.0f,
            indepYear = 1947,
            population = 15000000,
            lifeExpectancy = 70.0f,
            gnp = 1500.0f,
            gnpOld = 1400.0f,
            localName = "Test Local Name 3",
            governmentForm = "Republic",
            headOfState = "Test Head 3",
            capital = 3,
            code2 = "T3"
        )
        entityManager.persist(country3)

        entityManager.flush()
    }

    @Test
    fun `findByName should return countries with matching name`() {
        // When
        val result = countryRepository.findByName("Test Country 1")

        // Then
        assertEquals(1, result.size)
        assertEquals("TS1", result[0].code)
        assertEquals("Test Country 1", result[0].name)
    }

    @Test
    fun `findByContinent should return countries in the given continent`() {
        // When
        val result = countryRepository.findByContinent("Asia")

        // Then
        assertEquals(2, result.size)
        assertEquals("Asia", result[0].continent)
        assertEquals("Asia", result[1].continent)
    }

    @Test
    fun `findByPopulationGreaterThan should return countries with population greater than the given value`() {
        // When
        val result = countryRepository.findByPopulationGreaterThan(8000000)

        // Then
        assertEquals(2, result.size)
        assertEquals("Test Country 2", result[0].name)
        assertEquals("Test Country 3", result[1].name)
    }
}
