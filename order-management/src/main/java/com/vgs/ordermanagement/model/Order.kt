package com.vgs.ordermanagement.model

import com.vgs.ordermanagement.model.common.Identifier
import com.vgs.ordermanagement.model.common.LabeledEntity
import com.vgs.ordermanagement.model.common.Money
import com.vgs.ordermanagement.model.common.OrderId
import com.vgs.ordermanagement.model.common.UserId
import com.vgs.ordermanagement.model.common.VideoGameId
import com.vgs.ordermanagement.model.enums.OrderStatus
import com.vgs.ordermanagement.model.events.OrderCreatedEvent
import com.vgs.ordermanagement.model.events.OrderDeletedEvent
import com.vgs.ordermanagement.model.events.OrderStatusUpdatedEvent
import com.vgs.ordermanagement.model.exceptions.VideoGameNotAvailableException
import com.vgs.ordermanagement.repositories.CatalogRepository
import jakarta.persistence.AttributeOverride
import jakarta.persistence.Column
import jakarta.persistence.Embedded
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Table
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.modelling.command.AggregateLifecycle
import org.axonframework.spring.stereotype.Aggregate
import java.time.ZonedDateTime

@Aggregate(repository = "axonOrderRepository")
@Entity
@Table(name = "orders")
class Order : LabeledEntity {
    @AggregateIdentifier
    @EmbeddedId
    @AttributeOverride(name = "value", column = Column(name = "id"))
    private lateinit var id: OrderId

    private lateinit var updatedAt: ZonedDateTime

    @Enumerated(EnumType.STRING)
    private lateinit var status: OrderStatus

    private lateinit var amount: Money

    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "video_game_id"))
    private lateinit var videoGameId: VideoGameId

    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "user_id"))
    private lateinit var userId: UserId


    @CommandHandler
    constructor(
        command: CreateOrderCommand,
    ) {
        val event = OrderCreatedEvent(
            id = command.id,
            updatedAt = command.updatedAt,
            status = command.status,
            amount = command.amount,
            videoGameId = command.videoGameId,
            userId = command.userId,
        )
        this.on(event)
        AggregateLifecycle.apply(event)
    }

    fun on(event: OrderCreatedEvent) {
        this.id = event.id
        this.updatedAt = event.updatedAt
        this.amount = event.amount
        this.status = event.status
        this.videoGameId = event.videoGameId
        this.userId = event.userId
    }

    @CommandHandler
    fun updateStatus(command: UpdateStatusCommand) {
        val event = OrderStatusUpdatedEvent(
            id = command.id,
            updatedAt = command.updatedAt,
            status = command.status
        )
        this.on(event)
        AggregateLifecycle.apply(event)
    }

    fun on(event: OrderStatusUpdatedEvent) {
        this.status = event.status
    }

    @CommandHandler
    fun handle(command: DeleteOrderCommand) {
        val event = OrderDeletedEvent(
            id = command.id,
        )
        this.on(event)
        AggregateLifecycle.apply(event)
    }

    fun on(event: OrderDeletedEvent) {
        AggregateLifecycle.markDeleted()
    }

    override fun getId(): Identifier<out Any> {
        return this.id
    }

    override fun getLabel(): String {
        return "Order: $id"
    }

}