package com.vgs.ordermanagement.handlers

import com.vgs.ordermanagement.model.DeleteOrderCommand
import com.vgs.ordermanagement.model.DeleteOrdersHistoryCommand
import com.vgs.ordermanagement.model.common.UserId
import com.vgs.ordermanagement.model.events.VideoGameCreatedEvent
import com.vgs.ordermanagement.model.events.VideoGameDeletedEvent
import com.vgs.ordermanagement.model.events.OrdersHistoryDeletedEvent
import com.vgs.ordermanagement.model.events.VideoGameCapacityChangedEvent
import com.vgs.ordermanagement.model.events.VideoGameUpdatedEvent
import com.vgs.ordermanagement.model.exceptions.VideoGameNotFoundException
import com.vgs.ordermanagement.model.views.CatalogView
import com.vgs.ordermanagement.repositories.CatalogRepository
import com.vgs.ordermanagement.repositories.OrderRepository
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.commandhandling.gateway.CommandGateway
import org.axonframework.eventhandling.EventHandler
import org.springframework.stereotype.Component

@Component
class OrderExternalEventHandler(
    val commandGateway: CommandGateway,
    private val catalogRepository: CatalogRepository,
) {
    @EventHandler
    fun handle(event: VideoGameCreatedEvent) {
        catalogRepository.save(
            CatalogView(
                id = event.videoGameId,
                title = event.name,
                price = event.price,
                userId = event.userId,
                updatedAt = event.updatedAt,
                capacity = event.capacity,
            )
        )
    }

    @EventHandler
    fun handle(event: VideoGameUpdatedEvent) {
        if (catalogRepository.existsById(event.videoGameId)) {
            val existing = catalogRepository.findById(event.videoGameId).get()
            catalogRepository.save(
                existing.copy(
                    id = event.videoGameId,
                    title = event.name,
                    price = event.price,
                    userId = event.userId,
                    updatedAt = event.updatedAt,
                    capacity = event.capacity,
                )
            )
        } else throw VideoGameNotFoundException(event.videoGameId)
    }

    @EventHandler
    fun handle(event: VideoGameCapacityChangedEvent) {
        if (catalogRepository.existsById(event.videoGameId)) {
            val existing = catalogRepository.findById(event.videoGameId).get()
            catalogRepository.save(
                existing.copy(
                    capacity = existing.capacity + event.capacity,
                    updatedAt = event.updatedAt,
                )
            )
        } else throw VideoGameNotFoundException(event.videoGameId)
    }

    @EventHandler
    fun handle(event: VideoGameDeletedEvent) {
        if (catalogRepository.existsById(event.videoGameId)) {
            catalogRepository.deleteById(event.videoGameId)
        }
    }

    @EventHandler
    fun handle(event: OrdersHistoryDeletedEvent) {
        commandGateway.sendAndWait<UserId>(
            DeleteOrdersHistoryCommand(
                userId = event.userId
            )
        )
    }
}

@Component
class OrdersHistoryCommandHandler(
    private val orderRepository: OrderRepository,
    private val commandGateway: CommandGateway,
) {
    @CommandHandler
    fun handle(command: DeleteOrdersHistoryCommand) {
        val orderIds = orderRepository.findAllByUserId(command.userId).map { it.id }

        orderIds.forEach { orderId ->
            commandGateway.sendAndWait<Void>(
                DeleteOrderCommand(id = orderId)
            )
        }
    }
}