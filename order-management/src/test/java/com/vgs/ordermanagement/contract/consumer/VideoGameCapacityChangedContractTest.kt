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
import com.vgs.ordermanagement.model.common.Money
import com.vgs.ordermanagement.model.common.UserId
import com.vgs.ordermanagement.model.common.VideoGameTitle
import com.vgs.ordermanagement.model.events.VideoGameCapacityChangedEvent
import com.vgs.ordermanagement.model.views.CatalogView
import com.vgs.ordermanagement.repositories.CatalogRepository
import org.axonframework.commandhandling.gateway.CommandGateway
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.argThat
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import java.time.ZonedDateTime
import java.util.Optional

@ExtendWith(PactConsumerTestExt::class)
@PactTestFor(
    providerName = "catalog-service",
    providerType = ProviderType.ASYNCH
)
class VideoGameCapacityChangedContractTest {

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
    fun videoGameCapacityChangedPact(
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

        body.integerType("capacity", 5)

        body.stringMatcher(
            "updatedAt",
            "\\d{4}-\\d{2}-\\d{2}T.*",
            "2024-01-03T10:15:30+01:00[Europe/Warsaw]"
        )

        return builder
            .expectsToReceive(
                "VideoGameCapacityChangedEvent when capacity changes"
            )
            .withContent(body)
            .toPact()
    }

    @Test
    @PactTestFor(
        pactMethod = "videoGameCapacityChangedPact",
        providerType = ProviderType.ASYNCH
    )
    fun testVideoGameCapacityChangedPact(messages: List<Message>) {

        val message = messages.single()

        val json = message.contents.valueAsString()

        val event = objectMapper.readValue(
            json,
            VideoGameCapacityChangedEvent::class.java
        )

        val existing = CatalogView(
            id = event.videoGameId,
            title = VideoGameTitle(
                "The Witcher 3"
            ),
            price = Money(
                59.99,
                "EUR"
            ),
            userId = UserId(
                "VideoGameStoreUser:550e8400-e29b-41d4-a716-446655440001"
            ),
            updatedAt = ZonedDateTime.parse(
                "2024-01-01T10:15:30+01:00[Europe/Warsaw]"
            ),
            capacity = 10
        )

        `when`(
            catalogRepository.existsById(event.videoGameId)
        ).thenReturn(true)

        `when`(
            catalogRepository.findById(event.videoGameId)
        ).thenReturn(Optional.of(existing))

        handler.handle(event)

        verify(catalogRepository).save(
            argThat { catalog ->
                catalog.id.value ==
                        "VideoGame:550e8400-e29b-41d4-a716-446655440000" &&
                        catalog.capacity == 15 &&
                        catalog.updatedAt ==
                        event.updatedAt
            }
        )
    }
}
