package com.vgs.ordermanagement.model.events

import com.fasterxml.jackson.annotation.JsonIgnore
import com.vgs.ordermanagement.model.CreateOrderCommand
import com.vgs.ordermanagement.model.DeleteOrderCommand
import com.vgs.ordermanagement.model.DeleteOrdersHistoryCommand
import com.vgs.ordermanagement.model.Order
import com.vgs.ordermanagement.model.UpdateStatusCommand
import com.vgs.ordermanagement.model.common.Money
import com.vgs.ordermanagement.model.common.OrderId
import com.vgs.ordermanagement.model.common.UserId
import com.vgs.ordermanagement.model.common.VideoGameId
import com.vgs.ordermanagement.model.enums.OrderStatus
import java.time.ZonedDateTime

abstract class OrderEvent(
    open val id: OrderId
) : AbstractEvent(id) {
    @JsonIgnore
    override val aggregateClass = Order::class.java
}

data class OrderCreatedExternalEvent(
    val id: OrderId,
    val updatedAt: ZonedDateTime,
    val amount: Money,
    val status: OrderStatus,
    val userId: UserId,
    val videoGameId: VideoGameId,
)

data class OrderCreatedEvent(
    override val id: OrderId,
    val updatedAt: ZonedDateTime,
    val amount: Money,
    val status: OrderStatus,
    val userId: UserId,
    val videoGameId: VideoGameId,
) : OrderEvent(id) {
    constructor(command: CreateOrderCommand) : this(
        id = command.id,
        updatedAt = command.updatedAt,
        amount = command.amount,
        status = command.status,
        userId = command.userId,
        videoGameId = command.videoGameId,
    )

    override fun toExternalEvent(): OrderCreatedExternalEvent {
        return OrderCreatedExternalEvent(
            id,
            updatedAt,
            amount,
            status,
            userId,
            videoGameId
        )
    }
}

data class OrderStatusUpdatedExternalEvent(
    val id: OrderId,
    val updatedAt: ZonedDateTime,
    val status: OrderStatus,
)

data class OrderStatusUpdatedEvent(
    override val id: OrderId,
    val updatedAt: ZonedDateTime,
    val status: OrderStatus,
) : OrderEvent(id) {
    constructor(command: UpdateStatusCommand) : this(
        id = command.id,
        updatedAt = command.updatedAt,
        status = command.status
    )

    override fun toExternalEvent(): Any? {
        return OrderStatusUpdatedExternalEvent(
            id,
            updatedAt,
            status
        )
    }
}

data class OrderDeletedExternalEvent(
    val id: OrderId,
)

data class OrderDeletedEvent(
    override val id: OrderId,
) : OrderEvent(id) {
    constructor(command: DeleteOrderCommand) : this(
        id = command.id,
    )

    override fun toExternalEvent(): OrderDeletedExternalEvent {
        return OrderDeletedExternalEvent(
            id,
        )
    }
}