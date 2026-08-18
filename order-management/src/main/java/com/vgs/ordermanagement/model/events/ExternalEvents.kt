package com.vgs.ordermanagement.model.events

import com.vgs.ordermanagement.model.common.Money
import com.vgs.ordermanagement.model.common.UserId
import com.vgs.ordermanagement.model.common.VideoGameId
import com.vgs.ordermanagement.model.common.VideoGameTitle
import java.time.ZonedDateTime

data class OrdersHistoryDeletedEvent(
    val userId: UserId,
)

data class CatalogVideoGameAddedEvent(
    val videoGameId: VideoGameId,
    val title: VideoGameTitle,
    val price: Money,
    val userId: UserId,
    val updatedAt: ZonedDateTime = ZonedDateTime.now(),
    val capacity: Int = 0
)

data class CatalogVideoGameRemovedEvent(
    val videoGameId: VideoGameId,
)