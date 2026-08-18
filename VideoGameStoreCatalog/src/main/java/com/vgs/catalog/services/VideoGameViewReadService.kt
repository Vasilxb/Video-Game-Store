package com.vgs.catalog.services

import com.vgs.catalog.model.enums.Platform
import com.vgs.catalog.model.views.VideoGameView
import java.math.BigDecimal

interface VideoGameViewReadService {

    fun findAll(): List<VideoGameView>

    fun findByName(name: String): List<VideoGameView>

    fun findByStudio(studio: String): List<VideoGameView>

    fun findByYear(year: Int): List<VideoGameView>

    fun findByPlatform(platform: Platform): List<VideoGameView>

    fun findByRatingGreaterThanEqual(rating: Double): List<VideoGameView>

    fun findByRatingLessThanEqual(rating: Double): List<VideoGameView>

    fun findByPriceGreaterThanEqual(amount: BigDecimal): List<VideoGameView>

    fun findByPriceLessThanEqual(amount: BigDecimal): List<VideoGameView>

    fun getDistinctStudios(): List<String>

    fun getDistinctPlatforms(): List<Platform>
}