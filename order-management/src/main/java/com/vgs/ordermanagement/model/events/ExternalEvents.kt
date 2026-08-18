package com.vgs.ordermanagement.model.events

import com.vgs.ordermanagement.model.common.Money
import com.vgs.ordermanagement.model.common.UserId
import com.vgs.ordermanagement.model.common.VideoGameId
import com.vgs.ordermanagement.model.common.VideoGameTitle
import java.time.ZonedDateTime

data class OrdersHistoryDeletedEvent(
    val userId: UserId,
)

data class VideoGameCreatedEvent(
    val videoGameId: VideoGameId,
    val name: VideoGameTitle,
    val price: Money,
    val userId: UserId,
    val updatedAt: ZonedDateTime = ZonedDateTime.now(),
    val capacity: Int = 0
)

data class VideoGameDeletedEvent(
    val videoGameId: VideoGameId,
)

data class VideoGameUpdatedEvent(
    val videoGameId: VideoGameId,
    val name: VideoGameTitle,
    val price: Money,
    val userId: UserId,
    val updatedAt: ZonedDateTime = ZonedDateTime.now(),
    val capacity: Int = 0
)

data class VideoGameCapacityChangedEvent(
    val videoGameId: VideoGameId,
    val capacity: Int,
    val updatedAt: ZonedDateTime = ZonedDateTime.now(),
)