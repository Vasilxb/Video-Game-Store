package com.vgs.catalog.services.impl

import com.vgs.catalog.model.enums.Platform
import com.vgs.catalog.model.views.VideoGameView
import com.vgs.catalog.repositories.VideoGameViewJpaRepository
import com.vgs.catalog.services.VideoGameViewReadService
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class VideoGameViewReadServiceImpl(
    val videoGameViewJpaRepository: VideoGameViewJpaRepository
) : VideoGameViewReadService {

    override fun findAll(): List<VideoGameView> {
        return videoGameViewJpaRepository.findAll()
    }

    override fun findByName(name: String): List<VideoGameView> {
        return videoGameViewJpaRepository.findByName(name)
    }

    override fun findByStudio(studio: String): List<VideoGameView> {
        return videoGameViewJpaRepository.findByStudio(studio)
    }

    override fun findByYear(year: Int): List<VideoGameView> {
        return videoGameViewJpaRepository.findByYear(year)
    }

    override fun findByPlatform(platform: Platform): List<VideoGameView> {
        return videoGameViewJpaRepository.findByPlatform(platform)
    }

    override fun findByRatingGreaterThanEqual(rating: Double): List<VideoGameView> {
        return videoGameViewJpaRepository.findByRatingGreaterThanEqual(rating)
    }

    override fun findByRatingLessThanEqual(rating: Double): List<VideoGameView> {
        return videoGameViewJpaRepository.findByRatingLessThanEqual(rating)
    }

    override fun findByPriceGreaterThanEqual(amount: BigDecimal): List<VideoGameView> {
        return videoGameViewJpaRepository.findByPriceAmountGreaterThanEqual(amount)
    }

    override fun findByPriceLessThanEqual(amount: BigDecimal): List<VideoGameView> {
        return videoGameViewJpaRepository.findByPriceAmountLessThanEqual(amount)
    }

    override fun getDistinctStudios(): List<String> {
        return videoGameViewJpaRepository.findDistinctStudios()
    }

    override fun getDistinctPlatforms(): List<Platform> {
        return videoGameViewJpaRepository.findDistinctPlatforms()
    }
}