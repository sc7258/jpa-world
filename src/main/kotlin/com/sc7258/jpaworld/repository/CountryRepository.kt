package com.sc7258.jpaworld.repository

import com.sc7258.jpaworld.entity.Country
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CountryRepository : JpaRepository<Country, String> {
    fun findByName(name: String): List<Country>
    fun findByContinent(continent: String): List<Country>
    fun findByPopulationGreaterThan(population: Int): List<Country>
}