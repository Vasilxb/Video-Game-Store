package com.vgs.catalog.model.events

import com.fasterxml.jackson.annotation.JsonIgnore
import com.vgs.catalog.model.VideoGame
import com.vgs.catalog.model.common.Money
import com.vgs.catalog.model.common.UserId
import com.vgs.catalog.model.common.VideoGameId
import com.vgs.catalog.model.enums.Platform
import java.time.ZonedDateTime

abstract class VideoGameEvent(
    open val id: VideoGameId
) : AbstractEvent(id) {

    @JsonIgnore
    override val aggregateClass = VideoGame::class.java
}


data class VideoGameCreatedExternalEvent(
    val videoGameId: VideoGameId,
    val name: String,
    val price: Money,
    val userId: UserId,
    val updatedAt: ZonedDateTime = ZonedDateTime.now(),
    val capacity: Int = 0,
)


data class VideoGameUpdatedExternalEvent(
    val videoGameId: VideoGameId,
    val name: String,
    val price: Money,
    val userId: UserId,
    val updatedAt: ZonedDateTime = ZonedDateTime.now(),
    val capacity: Int = 0,
)


data class VideoGameDeletedExternalEvent(
    val videoGameId: VideoGameId,
)


data class VideoGameCapacityChangedExternalEvent(
    val videoGameId: VideoGameId,
    val capacity: Int,
    val updatedAt: ZonedDateTime = ZonedDateTime.now(),
)


data class VideoGameCreatedEvent(
    override val id: VideoGameId,
    val updatedAt: ZonedDateTime,
    val name: String,
    val price: Money,
    val platform: Platform,
    val year: Int,
    val studio: String,
    val rating: Double,
    val storeId: UserId,
    val capacity: Int
) : VideoGameEvent(id) {
    override fun toExternalEvent(): VideoGameCreatedExternalEvent = VideoGameCreatedExternalEvent(
        videoGameId = id,
        name = name,
        price = price,
        userId = storeId,
        updatedAt = updatedAt,
        capacity = capacity,
    )
}


data class VideoGameUpdatedEvent(
    override val id: VideoGameId,
    val updatedAt: ZonedDateTime,
    val name: String,
    val price: Money,
    val platform: Platform,
    val year: Int,
    val studio: String,
    val rating: Double,
    val storeId: UserId,
    val capacity: Int
) : VideoGameEvent(id) {
    override fun toExternalEvent(): VideoGameUpdatedExternalEvent = VideoGameUpdatedExternalEvent(
        videoGameId = id,
        name = name,
        price = price,
        userId = storeId,
        updatedAt = updatedAt,
        capacity = capacity,
    )
}


data class VideoGameDeletedEvent(
    override val id: VideoGameId,
    val updatedAt: ZonedDateTime
) : VideoGameEvent(id) {
    override fun toExternalEvent(): VideoGameDeletedExternalEvent = VideoGameDeletedExternalEvent(
        videoGameId = id,
    )
}


data class VideoGameCapacityChangedEvent(
    override val id: VideoGameId,
    val updatedAt: ZonedDateTime,
    val capacity: Int
) : VideoGameEvent(id) {
    override fun toExternalEvent(): VideoGameCapacityChangedExternalEvent = VideoGameCapacityChangedExternalEvent(
        videoGameId = id,
        capacity = capacity,
        updatedAt = updatedAt,
    )
}