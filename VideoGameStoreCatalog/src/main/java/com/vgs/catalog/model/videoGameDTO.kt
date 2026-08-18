package com.vgs.catalog.model

import com.vgs.catalog.model.common.Money
import com.vgs.catalog.model.common.UserId
import com.vgs.catalog.model.common.VideoGameId
import com.vgs.catalog.model.enums.Platform

data class CreateVideoGameCommandDto(
    val name: String,
    val price: Money,
    val platform: Platform,
    val year: Int,
    val studio: String,
    val rating: Double,
    val storeId: UserId,
    val capacity: Int
)

data class UpdateVideoGameCommandDto(
    val id: VideoGameId,
    val name: String,
    val price: Money,
    val platform: Platform,
    val year: Int,
    val studio: String,
    val rating: Double,
    val storeId: UserId,
    val capacity: Int
)

data class DeleteVideoGameCommandDto(
    val id: VideoGameId
)