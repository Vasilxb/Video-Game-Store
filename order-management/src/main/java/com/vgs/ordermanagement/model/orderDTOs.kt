package com.vgs.ordermanagement.model

import com.vgs.ordermanagement.model.common.OrderId
import com.vgs.ordermanagement.model.common.UserId
import com.vgs.ordermanagement.model.common.VideoGameId
import com.vgs.ordermanagement.model.enums.OrderStatus

data class CreateOrderCommandDto(
    val videoGameId: VideoGameId,
    val userId: UserId
)

data class UpdateOrderStatusCommandDto(
    val status: OrderStatus,
    val orderId: OrderId
)

data class DeleteOrderCommandDto(
    val orderId: OrderId
)