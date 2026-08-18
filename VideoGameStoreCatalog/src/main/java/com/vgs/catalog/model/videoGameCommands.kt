package com.vgs.catalog.model

import com.vgs.catalog.model.common.Money
import com.vgs.catalog.model.common.UserId
import com.vgs.catalog.model.common.VideoGameId
import com.vgs.catalog.model.enums.Platform
import org.axonframework.modelling.command.TargetAggregateIdentifier
import java.time.ZonedDateTime


data class CreateVideoGameCommand(
    val id: VideoGameId = VideoGameId(),
    val updatedAt: ZonedDateTime = ZonedDateTime.now(),
    val name: String,
    val price: Money,
    val platform: Platform,
    val year: Int,
    val studio: String,
    val rating: Double,
    val storeId: UserId,
    val capacity: Int
)


data class UpdateVideoGameCommand(
    @TargetAggregateIdentifier
    val id: VideoGameId,
    val updatedAt: ZonedDateTime = ZonedDateTime.now(),
    val name: String,
    val price: Money,
    val platform: Platform,
    val year: Int,
    val studio: String,
    val rating: Double,
    val storeId: UserId,
    val capacity: Int
)


data class DeleteVideoGameCommand(
    @TargetAggregateIdentifier
    val id: VideoGameId,
    val updatedAt: ZonedDateTime = ZonedDateTime.now()
)
data class DecreaseVideoGameCapacityCommand(
    @TargetAggregateIdentifier
    val id: VideoGameId
)

data class IncreaseVideoGameCapacityCommand(
    @TargetAggregateIdentifier
    val id: VideoGameId
)