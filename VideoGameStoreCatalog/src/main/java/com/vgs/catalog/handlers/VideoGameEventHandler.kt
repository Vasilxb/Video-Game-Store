package com.vgs.catalog.handlers

import com.vgs.catalog.model.events.VideoGameCapacityChangedEvent
import com.vgs.catalog.model.events.VideoGameCreatedEvent
import com.vgs.catalog.model.events.VideoGameDeletedEvent
import com.vgs.catalog.model.events.VideoGameUpdatedEvent
import com.vgs.catalog.model.views.VideoGameView
import com.vgs.catalog.repositories.VideoGameViewJpaRepository
import org.axonframework.eventhandling.EventHandler
import org.springframework.stereotype.Component

@Component
class VideoGameEventHandler(
    val videoGameViewRepository: VideoGameViewJpaRepository
) {

    @EventHandler
    fun on(event: VideoGameCreatedEvent) {

        val view = VideoGameView(
            id = event.id,
            updatedAt = event.updatedAt,
            name = event.name,
            price = event.price,
            platform = event.platform,
            year = event.year,
            studio = event.studio,
            rating = event.rating,
            storeId = event.storeId,
            capacity = event.capacity
        )

        videoGameViewRepository.save(view)
    }


    @EventHandler
    fun on(event: VideoGameUpdatedEvent) {

        val existing =
            videoGameViewRepository.findById(event.id)

        if (existing.isPresent) {

            val view = existing.get().copy(
                updatedAt = event.updatedAt,
                name = event.name,
                price = event.price,
                platform = event.platform,
                year = event.year,
                studio = event.studio,
                rating = event.rating,
                storeId = event.storeId,
                capacity = event.capacity
            )

            videoGameViewRepository.save(view)
        }
    }


    @EventHandler
    fun on(event: VideoGameCapacityChangedEvent) {

        val existing =
            videoGameViewRepository.findById(event.id)

        if (existing.isPresent) {

            val view = existing.get().copy(
                updatedAt = event.updatedAt,
                capacity = event.capacity
            )

            videoGameViewRepository.save(view)
        }
    }


    @EventHandler
    fun on(event: VideoGameDeletedEvent) {

        if (videoGameViewRepository.existsById(event.id)) {
            videoGameViewRepository.deleteById(event.id)
        }
    }
}