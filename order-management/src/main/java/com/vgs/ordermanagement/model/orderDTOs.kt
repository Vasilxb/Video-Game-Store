package com.vgs.ordermanagement.model

import com.vgs.ordermanagement.model.common.Money
import com.vgs.ordermanagement.model.common.OrderId
import com.vgs.ordermanagement.model.common.VideoGameId
import com.vgs.ordermanagement.model.enums.OrderStatus

data class CreateOrderCommandDto(
    val amount: Money,
    val status: OrderStatus,
    val videoGameId: VideoGameId
)

data class UpdateOrderStatusDto(
    val status: OrderStatus,
    val orderId: OrderId
)