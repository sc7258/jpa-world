package com.sc7258.jpaworld.repository

import com.sc7258.jpaworld.entity.Country
import com.sc7258.jpaworld.entity.CountryLanguage
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.test.context.ActiveProfiles

@DataJpaTest
@ActiveProfiles("unit-test")
class CountryLanguageRepositoryTest {

    @Autowired
    private lateinit var entityManager: TestEntityManager

    @Autowired
    private lateinit var countryLanguageRepository: CountryLanguageRepository

    private lateinit var country1: Country
    private lateinit var country2: Country
    private lateinit var language1: CountryLanguage
    private lateinit var language2: CountryLanguage
    private lateinit var language3: CountryLanguage

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

        // Create test languages
        language1 = CountryLanguage(
            country = country1,
            language = "Language 1",
            isOfficial = "T",
            percentage = 80.0f
        )
        entityManager.persist(language1)

        language2 = CountryLanguage(
            country = country1,
            language = "Language 2",
            isOfficial = "F",
            percentage = 20.0f
        )
        entityManager.persist(language2)

        language3 = CountryLanguage(
            country = country2,
            language = "Language 3",
            isOfficial = "T",
            percentage = 90.0f
        )
        entityManager.persist(language3)

        entityManager.flush()
    }

    @Test
    fun `findByCountry should return languages for the given country`() {
        // When
        val result = countryLanguageRepository.findByCountry(country1)

        // Then
        assertEquals(2, result.size)
        assertEquals("Language 1", result[0].language)
        assertEquals("Language 2", result[1].language)
    }

    @Test
    fun `findByLanguage should return entries with matching language`() {
        // When
        val result = countryLanguageRepository.findByLanguage("Language 1")

        // Then
        assertEquals(1, result.size)
        assertEquals("TS1", result[0].country.code)
        assertEquals("Language 1", result[0].language)
    }

    @Test
    fun `findByIsOfficial should return languages with matching official status`() {
        // When
        val result = countryLanguageRepository.findByIsOfficial("T")

        // Then
        assertEquals(2, result.size)
        assertEquals("T", result[0].isOfficial)
        assertEquals("T", result[1].isOfficial)
    }

    @Test
    fun `findByPercentageGreaterThan should return languages with percentage greater than the given value`() {
        // When
        val result = countryLanguageRepository.findByPercentageGreaterThan(50.0f)

        // Then
        assertEquals(2, result.size)
        assertEquals(80.0f, result[0].percentage)
        assertEquals(90.0f, result[1].percentage)
    }
}
