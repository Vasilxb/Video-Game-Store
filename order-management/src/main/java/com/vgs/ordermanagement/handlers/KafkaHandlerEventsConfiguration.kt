package com.vgs.ordermanagement.handlers

import com.fasterxml.jackson.databind.ObjectMapper
import com.vgs.ordermanagement.model.events.VideoGameCapacityChangedEvent
import com.vgs.ordermanagement.model.events.VideoGameCreatedEvent
import com.vgs.ordermanagement.model.events.VideoGameDeletedEvent
import com.vgs.ordermanagement.model.events.VideoGameUpdatedEvent
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.axonframework.eventhandling.gateway.EventGateway
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class KafkaHandlerEventsConfiguration(
    private val eventGateway: EventGateway,
    private val objectMapper: ObjectMapper,
) {

    @KafkaListener(topics = ["video-game.created"], groupId = "catalog-events")
    fun onCreated(record: ConsumerRecord<String, String>) {
        eventGateway.publish(objectMapper.readValue(record.value(), VideoGameCreatedEvent::class.java))
    }

    @KafkaListener(topics = ["video-game.updated"], groupId = "catalog-events")
    fun onUpdated(record: ConsumerRecord<String, String>) {
        eventGateway.publish(objectMapper.readValue(record.value(), VideoGameUpdatedEvent::class.java))
    }

    @KafkaListener(topics = ["video-game.capacity-changed"], groupId = "catalog-events")
    fun onCapacityChanged(record: ConsumerRecord<String, String>) {
        eventGateway.publish(objectMapper.readValue(record.value(), VideoGameCapacityChangedEvent::class.java))
    }

    @KafkaListener(topics = ["video-game.deleted"], groupId = "catalog-events")
    fun onDeleted(record: ConsumerRecord<String, String>) {
        eventGateway.publish(objectMapper.readValue(record.value(), VideoGameDeletedEvent::class.java))
    }
}