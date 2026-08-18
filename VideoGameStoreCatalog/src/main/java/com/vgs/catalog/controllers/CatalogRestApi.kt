package com.vgs.catalog.controllers

import com.vgs.catalog.model.enums.Platform
import com.vgs.catalog.model.views.VideoGameView
import com.vgs.catalog.services.VideoGameViewReadService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.http.ResponseEntity
import org.springframework.http.HttpStatus
import java.math.BigDecimal

@RestController
@RequestMapping("/api/catalog")
class CatalogRestApi(
    val videoGameViewReadService: VideoGameViewReadService
) {

    @GetMapping("/all")
    fun findAll(): List<VideoGameView> {
        return videoGameViewReadService.findAll()
    }

    @GetMapping("/by-name/{name}")
    fun findByName(
        @PathVariable name: String
    ): ResponseEntity<List<VideoGameView>> {
        val results = videoGameViewReadService.findByName(name)
        return if (results.isEmpty()) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).build()
        } else {
            ResponseEntity.ok(results)
        }
    }

    @GetMapping("/by-studio/{studio}")
    fun findByStudio(@PathVariable studio: String): List<VideoGameView> {
        return videoGameViewReadService.findByStudio(studio)
    }

    @GetMapping("/by-year/{year}")
    fun findByYear(@PathVariable year: Int): List<VideoGameView> {
        return videoGameViewReadService.findByYear(year)
    }

    @GetMapping("/by-platform/{platform}")
    fun findByPlatform(@PathVariable platform: Platform): List<VideoGameView> {
        return videoGameViewReadService.findByPlatform(platform)
    }

    @GetMapping("/by-rating-gte/{rating}")
    fun findByRatingGte(@PathVariable rating: Double): List<VideoGameView> {
        return videoGameViewReadService.findByRatingGreaterThanEqual(rating)
    }

    @GetMapping("/by-rating-lte/{rating}")
    fun findByRatingLte(@PathVariable rating: Double): List<VideoGameView> {
        return videoGameViewReadService.findByRatingLessThanEqual(rating)
    }

    @GetMapping("/by-price-gte/{amount}")
    fun findByPriceGte(@PathVariable amount: BigDecimal): List<VideoGameView> {
        return videoGameViewReadService.findByPriceGreaterThanEqual(amount)
    }

    @GetMapping("/by-price-lte/{amount}")
    fun findByPriceLte(@PathVariable amount: BigDecimal): List<VideoGameView> {
        return videoGameViewReadService.findByPriceLessThanEqual(amount)
    }

    @GetMapping("/studios")
    fun getStudios(): List<String> = videoGameViewReadService.getDistinctStudios()

    @GetMapping("/platforms")
    fun getPlatforms(): List<Platform> = videoGameViewReadService.getDistinctPlatforms()
}