package com.vgs.ordermanagement.handlers

import com.vgs.ordermanagement.model.events.OrderCreatedEvent
import com.vgs.ordermanagement.model.exceptions.VideoGameNotAvailableException
import com.vgs.ordermanagement.repositories.CatalogRepository
import org.axonframework.eventhandling.EventHandler
import org.springframework.stereotype.Component

@Component
class OrderInternalEventHandler(
    private val catalogRepository: CatalogRepository,
) {
    @EventHandler
    fun handle(event: OrderCreatedEvent) {
        val game = catalogRepository.findById(event.videoGameId)
            .orElseThrow { VideoGameNotAvailableException(event.videoGameId) }

        catalogRepository.save(
            game.copy(
                capacity = game.capacity - 1,
                updatedAt = event.updatedAt,
            )
        )
        catalogRepository.save(game)
    }
}