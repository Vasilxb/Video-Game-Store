package com.vgs.catalog.mcp

import com.vgs.catalog.model.enums.Platform
import com.vgs.catalog.model.views.VideoGameView
import com.vgs.catalog.services.VideoGameViewReadService
import org.springframework.ai.tool.annotation.Tool
import org.springframework.ai.tool.annotation.ToolParam
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.math.RoundingMode

@Service
class CatalogMcpTools(
    private val videoGameViewReadService: VideoGameViewReadService
) {

    @Tool(
        name = "list_video_games",
        description = "List all video games currently available in the Catalog read model."
    )
    fun listVideoGames(): CatalogGamesMcpResponse {
        val games = videoGameViewReadService
            .findAll()
            .map { it.toMcpDto() }

        return CatalogGamesMcpResponse(
            message = "Found ${games.size} video game(s).",
            count = games.size,
            games = games
        )
    }


    @Tool(
        name = "find_video_games_by_name",
        description = "Find video games by exact name from the Catalog read model."
    )
    fun findVideoGamesByName(
        @ToolParam(description = "Exact video game name, for example Elden Ring")
        name: String
    ): CatalogGamesMcpResponse {
        val games = videoGameViewReadService
            .findByName(name)
            .map { it.toMcpDto() }

        return CatalogGamesMcpResponse(
            message = "Found ${games.size} video game(s) with name '$name'.",
            count = games.size,
            games = games
        )
    }


    @Tool(
        name = "find_video_games_by_platform",
        description = "Find video games by platform from the Catalog read model."
    )
    fun findVideoGamesByPlatform(
        @ToolParam(description = "Platform name, for example PC, PLAYSTATION, or XBOX")
        platform: String
    ): CatalogGamesMcpResponse {
        val parsedPlatform = parsePlatform(platform)

        if (parsedPlatform == null) {
            return CatalogGamesMcpResponse(
                message = "Invalid platform '$platform'. Allowed platforms: ${allowedPlatforms()}.",
                count = 0,
                games = emptyList()
            )
        }

        val games = videoGameViewReadService
            .findByPlatform(parsedPlatform)
            .map { it.toMcpDto() }

        return CatalogGamesMcpResponse(
            message = "Found ${games.size} video game(s) for platform '${parsedPlatform.name}'.",
            count = games.size,
            games = games
        )
    }


    @Tool(
        name = "find_low_capacity_games",
        description = "Find video games with capacity less than or equal to the provided threshold."
    )
    fun findLowCapacityGames(
        @ToolParam(description = "Maximum capacity threshold, for example 5")
        threshold: Int
    ): CatalogGamesMcpResponse {
        val safeThreshold = threshold.coerceAtLeast(0)

        val games = videoGameViewReadService
            .findAll()
            .filter { it.capacity <= safeThreshold }
            .map { it.toMcpDto() }

        return CatalogGamesMcpResponse(
            message = "Found ${games.size} video game(s) with capacity <= $safeThreshold.",
            count = games.size,
            games = games
        )
    }


    @Tool(
        name = "get_catalog_summary",
        description = "Get a summary of the Catalog read model, including total games, capacity, studios, platforms, and low-capacity games."
    )
    fun getCatalogSummary(): CatalogSummaryMcpResponse {
        val games = videoGameViewReadService.findAll()
        val lowCapacityThreshold = 5

        val lowCapacityGames = games
            .filter { it.capacity <= lowCapacityThreshold }
            .map { it.toMcpDto() }

        return CatalogSummaryMcpResponse(
            totalGames = games.size,
            totalCapacity = games.sumOf { it.capacity },
            averageRating = games.averageRating(),
            studios = videoGameViewReadService
                .getDistinctStudios()
                .sorted(),
            platforms = videoGameViewReadService
                .getDistinctPlatforms()
                .map { it.name }
                .sorted(),
            lowCapacityThreshold = lowCapacityThreshold,
            lowCapacityGameCount = lowCapacityGames.size,
            lowCapacityGames = lowCapacityGames
        )
    }


    private fun parsePlatform(platform: String): Platform? {
        return try {
            Platform.valueOf(platform.uppercase())
        } catch (ex: IllegalArgumentException) {
            null
        }
    }


    private fun allowedPlatforms(): String {
        return Platform.values()
            .joinToString(", ") { it.name }
    }


    private fun List<VideoGameView>.averageRating(): Double {
        if (isEmpty()) {
            return 0.0
        }

        return BigDecimal
            .valueOf(map { it.rating }.average())
            .setScale(2, RoundingMode.HALF_UP)
            .toDouble()
    }


    private fun VideoGameView.toMcpDto(): CatalogMcpVideoGame {
        return CatalogMcpVideoGame(
            id = id.value,
            name = name,
            priceAmount = price.amount.toPlainString(),
            priceCurrency = price.currency,
            platform = platform.name,
            year = year,
            studio = studio,
            rating = rating,
            storeId = storeId.value,
            capacity = capacity,
            updatedAt = updatedAt.toString()
        )
    }
}


data class CatalogMcpVideoGame(
    val id: String,
    val name: String,
    val priceAmount: String,
    val priceCurrency: String,
    val platform: String,
    val year: Int,
    val studio: String,
    val rating: Double,
    val storeId: String,
    val capacity: Int,
    val updatedAt: String
)


data class CatalogGamesMcpResponse(
    val message: String,
    val count: Int,
    val games: List<CatalogMcpVideoGame>
)


data class CatalogSummaryMcpResponse(
    val totalGames: Int,
    val totalCapacity: Int,
    val averageRating: Double,
    val studios: List<String>,
    val platforms: List<String>,
    val lowCapacityThreshold: Int,
    val lowCapacityGameCount: Int,
    val lowCapacityGames: List<CatalogMcpVideoGame>
)