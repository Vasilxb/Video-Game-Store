package com.vgs.ordermanagement.contract.consumer

import au.com.dius.pact.consumer.MessagePactBuilder
import au.com.dius.pact.consumer.Pact
import au.com.dius.pact.consumer.dsl.PactDslJsonBody
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt
import au.com.dius.pact.consumer.junit5.PactTestFor
import au.com.dius.pact.consumer.junit5.ProviderType
import au.com.dius.pact.model.v3.messaging.Message
import au.com.dius.pact.model.v3.messaging.MessagePact
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.vgs.ordermanagement.handlers.OrderExternalEventHandler
import com.vgs.ordermanagement.model.DeleteOrdersHistoryCommand
import com.vgs.ordermanagement.model.common.UserId
import com.vgs.ordermanagement.model.events.OrdersHistoryDeletedEvent
import com.vgs.ordermanagement.repositories.CatalogRepository
import org.axonframework.commandhandling.gateway.CommandGateway
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.mockito.ArgumentCaptor


@ExtendWith(PactConsumerTestExt::class)
@PactTestFor(
    providerName = "users-service",
    providerType = ProviderType.ASYNCH
)
class OrdersHistoryDeletedContractTest {

    private val catalogRepository = mock<CatalogRepository>()
    private val commandGateway = mock<CommandGateway>()

    private val handler = OrderExternalEventHandler(
        commandGateway = commandGateway,
        catalogRepository = catalogRepository
    )

    private val objectMapper = ObjectMapper()
        .registerModule(KotlinModule.Builder().build())
        .registerModule(JavaTimeModule())
        .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)

    @Pact(consumer = "order-management")
    fun ordersHistoryDeletedPact(
        builder: MessagePactBuilder
    ): MessagePact {

        val body = PactDslJsonBody()

        body
            .`object`("userId")
            .stringMatcher(
                "value",
                "VideoGameStoreUser:[0-9a-fA-F-]{36}",
                "VideoGameStoreUser:550e8400-e29b-41d4-a716-446655440001"
            )
            .closeObject()

        return builder
            .expectsToReceive(
                "OrdersHistoryDeletedEvent when order history is deleted"
            )
            .withContent(body)
            .toPact()
    }

    @Test
    @PactTestFor(
        pactMethod = "ordersHistoryDeletedPact",
        providerType = ProviderType.ASYNCH
    )
    fun testOrdersHistoryDeletedPact(messages: List<Message>) {

        val message = messages.single()

        val json = message.contents.valueAsString()

        val event = objectMapper.readValue(
            json,
            OrdersHistoryDeletedEvent::class.java
        )

        handler.handle(event)

        val captor = ArgumentCaptor.forClass(
            DeleteOrdersHistoryCommand::class.java
        )

        verify(commandGateway).sendAndWait<UserId>(captor.capture())

        assertEquals(
            "VideoGameStoreUser:550e8400-e29b-41d4-a716-446655440001",
            captor.value.userId.value
        )
    }

}
