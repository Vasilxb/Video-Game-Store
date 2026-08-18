package com.vgs.ordermanagement.model

import com.vgs.ordermanagement.model.common.Money
import com.vgs.ordermanagement.model.common.OrderId
import com.vgs.ordermanagement.model.common.UserId
import com.vgs.ordermanagement.model.common.VideoGameId
import com.vgs.ordermanagement.model.enums.OrderStatus
import org.axonframework.modelling.command.TargetAggregateIdentifier
import java.time.ZonedDateTime

data class CreateOrderCommand(
    val id: OrderId = OrderId(),
    val updatedAt: ZonedDateTime = ZonedDateTime.now(),
    var amount: Money,
    var status: OrderStatus,
    var userId: UserId,
    var videoGameId: VideoGameId
)

data class UpdateStatusCommand(
    @TargetAggregateIdentifier
    val id: OrderId,
    val updatedAt: ZonedDateTime = ZonedDateTime.now(),
    val status: OrderStatus
)

data class DeleteOrderCommand(
    @TargetAggregateIdentifier
    val id: OrderId,
)

data class DeleteOrdersHistoryCommand(
    val userId: UserId,
)