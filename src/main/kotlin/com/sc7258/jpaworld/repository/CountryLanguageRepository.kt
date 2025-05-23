package com.sc7258.jpaworld.repository

import com.sc7258.jpaworld.entity.Country
import com.sc7258.jpaworld.entity.CountryLanguage
import com.sc7258.jpaworld.entity.CountryLanguageId
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CountryLanguageRepository : JpaRepository<CountryLanguage, CountryLanguageId> {
    fun findByCountry(country: Country): List<CountryLanguage>
    fun findByLanguage(language: String): List<CountryLanguage>
    fun findByIsOfficial(isOfficial: String): List<CountryLanguage>
    fun findByPercentageGreaterThan(percentage: Float): List<CountryLanguage>
}