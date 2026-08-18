package com.vgs.catalog.repositories

import com.vgs.catalog.model.common.VideoGameId
import com.vgs.catalog.model.views.VideoGameView
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import com.vgs.catalog.model.enums.Platform

@Repository
interface VideoGameViewJpaRepository :
    JpaRepository<VideoGameView, VideoGameId> {

    fun findByName(name: String): List<VideoGameView>

    fun findByStudio(studio: String): List<VideoGameView>

    fun findByYear(year: Int): List<VideoGameView>

    fun findByPlatform(platform: Platform): List<VideoGameView>

    fun findByRatingGreaterThanEqual(rating: Double): List<VideoGameView>

    fun findByRatingLessThanEqual(rating: Double): List<VideoGameView>

    fun findByPriceAmountGreaterThanEqual(amount: java.math.BigDecimal): List<VideoGameView>

    fun findByPriceAmountLessThanEqual(amount: java.math.BigDecimal): List<VideoGameView>

    @Query("SELECT DISTINCT v.studio FROM VideoGameView v")
    fun findDistinctStudios(): List<String>

    @Query("SELECT DISTINCT v.platform FROM VideoGameView v")
    fun findDistinctPlatforms(): List<Platform>
}