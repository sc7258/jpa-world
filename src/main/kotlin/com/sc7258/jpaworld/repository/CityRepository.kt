package com.sc7258.jpaworld.repository

import com.sc7258.jpaworld.entity.City
import com.sc7258.jpaworld.entity.Country
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CityRepository : JpaRepository<City, Long> {
    fun findByName(name: String): List<City>
    fun findByCountry(country: Country): List<City>
    fun findByDistrict(district: String): List<City>
    fun findByPopulationGreaterThan(population: Int): List<City>
}