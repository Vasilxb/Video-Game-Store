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
) : VideoGameEvent(id)


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
) : VideoGameEvent(id)


data class VideoGameDeletedEvent(
    override val id: VideoGameId,
    val updatedAt: ZonedDateTime
) : VideoGameEvent(id)


data class VideoGameCapacityChangedEvent(
    override val id: VideoGameId,
    val updatedAt: ZonedDateTime,
    val capacity: Int
) : VideoGameEvent(id)