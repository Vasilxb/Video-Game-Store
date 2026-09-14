package com.vgs.ordermanagement.handlers

import com.vgs.ordermanagement.model.events.VideoGameCreatedEvent
import com.vgs.ordermanagement.model.events.VideoGameDeletedEvent
import com.vgs.ordermanagement.model.events.VideoGameCapacityChangedEvent
import com.vgs.ordermanagement.model.events.VideoGameUpdatedEvent
import com.vgs.ordermanagement.model.exceptions.VideoGameNotFoundException
import com.vgs.ordermanagement.model.views.CatalogView
import com.vgs.ordermanagement.repositories.CatalogRepository
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
}