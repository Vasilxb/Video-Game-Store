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
import com.vgs.ordermanagement.handlers.OrderExternalEventHandler
import com.vgs.ordermanagement.model.events.VideoGameCreatedEvent
import com.vgs.ordermanagement.repositories.CatalogRepository
import org.axonframework.commandhandling.gateway.CommandGateway
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.argThat
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import java.math.BigDecimal
import com.fasterxml.jackson.module.kotlin.KotlinModule


@ExtendWith(PactConsumerTestExt::class)
@PactTestFor(
    providerName = "catalog-service",
    providerType = ProviderType.ASYNCH
)
class VideoGameCreatedContractTest {
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
    fun videoGameCreatedPact(
        builder: MessagePactBuilder
    ): MessagePact {

        val body = PactDslJsonBody()

        body
            .`object`("videoGameId")
            .stringMatcher(
                "value",
                "VideoGame:[0-9a-fA-F-]{36}",
                "VideoGame:550e8400-e29b-41d4-a716-446655440000"
            )
            .closeObject()

        body
            .`object`("name")
            .stringType("value", "The Witcher 3")
            .closeObject()

        body
            .`object`("price")
            .decimalType("amount", 59.99)
            .stringType("currency", "EUR")
            .closeObject()

        body
            .`object`("userId")
            .stringMatcher(
                "value",
                "VideoGameStoreUser:[0-9a-fA-F-]{36}",
                "VideoGameStoreUser:550e8400-e29b-41d4-a716-446655440001"
            )
            .closeObject()

        body.stringMatcher(
            "updatedAt",
            "\\d{4}-\\d{2}-\\d{2}T.*",
            "2024-01-01T10:15:30+01:00[Europe/Warsaw]"
        )

        body.integerType("capacity", 10)

        return builder
            .expectsToReceive(
                "VideoGameCreatedEvent when a video game is added"
            )
            .withContent(body)
            .toPact()
    }

    @Test
    @PactTestFor(
        pactMethod = "videoGameCreatedPact",
        providerType = ProviderType.ASYNCH
    )
    fun testVideoGameCreatedPact(messages: List<Message>) {
        val message = messages.single()

        val json = message.contents.valueAsString()

        val event = objectMapper.readValue(
            json,
            VideoGameCreatedEvent::class.java
        )

        handler.handle(event)

        verify(catalogRepository).save(
            argThat { catalog ->
                catalog.id.value ==
                        "VideoGame:550e8400-e29b-41d4-a716-446655440000" &&
                        catalog.title.value == "The Witcher 3" &&
                        catalog.price.amount == BigDecimal("59.99") &&
                        catalog.price.currency == "EUR" &&
                        catalog.userId.value ==
                        "VideoGameStoreUser:550e8400-e29b-41d4-a716-446655440001" &&
                        catalog.capacity == 10
            }
        )
    }
}
