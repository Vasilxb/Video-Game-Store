package com.vgs.catalog.model.events

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.vgs.catalog.model.common.VideoGameId

@JsonIgnoreProperties(ignoreUnknown = true)
data class OrderCreatedExternalEvent(
    val videoGameId: VideoGameId
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class OrderStatusUpdatedExternalEvent(
    val videoGameId: VideoGameId,
    val status: String
)