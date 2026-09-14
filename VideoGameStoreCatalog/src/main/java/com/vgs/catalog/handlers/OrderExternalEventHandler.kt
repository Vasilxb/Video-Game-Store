package com.vgs.catalog.handlers

import com.fasterxml.jackson.databind.ObjectMapper
import com.vgs.catalog.model.DecreaseVideoGameCapacityCommand
import com.vgs.catalog.model.events.OrderCreatedExternalEvent
import org.axonframework.commandhandling.gateway.CommandGateway
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component
import com.vgs.catalog.model.IncreaseVideoGameCapacityCommand
import com.vgs.catalog.model.events.OrderStatusUpdatedExternalEvent

@Component
class OrderExternalEventHandler(
    private val commandGateway: CommandGateway,
    private val objectMapper: ObjectMapper
) {

    @KafkaListener(
        topics = ["order.created"],
        groupId = "order-events"
    )
    fun handleOrderCreated(payload: String) {

        val event = objectMapper.readValue(
            payload,
            OrderCreatedExternalEvent::class.java
        )

        commandGateway.sendAndWait<Void>(
            DecreaseVideoGameCapacityCommand(
                id = event.videoGameId,
                amount = 1
            )
        )
    }
    @KafkaListener(
        topics = ["order.status.updated"],
        groupId = "order-events"

    )
    fun handleOrderStatusUpdated(payload: String) {

        val event = objectMapper.readValue(
            payload,
            OrderStatusUpdatedExternalEvent::class.java
        )

        if (event.status == "CANCELLED") {
            commandGateway.sendAndWait<Void>(
                IncreaseVideoGameCapacityCommand(
                    id = event.videoGameId,
                    amount = 1
                )
            )
        }
    }
}