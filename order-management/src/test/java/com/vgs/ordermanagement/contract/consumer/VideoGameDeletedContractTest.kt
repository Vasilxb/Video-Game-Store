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
import com.vgs.ordermanagement.model.events.VideoGameDeletedEvent
import com.vgs.ordermanagement.repositories.CatalogRepository
import org.axonframework.commandhandling.gateway.CommandGateway
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`

@ExtendWith(PactConsumerTestExt::class)
@PactTestFor(
    providerName = "catalog-service",
    providerType = ProviderType.ASYNCH
)
class VideoGameDeletedContractTest {

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
    fun videoGameDeletedPact(
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

        return builder
            .expectsToReceive(
                "VideoGameDeletedEvent when a video game is deleted"
            )
            .withContent(body)
            .toPact()
    }

    @Test
    @PactTestFor(
        pactMethod = "videoGameDeletedPact",
        providerType = ProviderType.ASYNCH
    )
    fun testVideoGameDeletedPact(messages: List<Message>) {

        val message = messages.single()

        val json = message.contents.valueAsString()

        val event = objectMapper.readValue(
            json,
            VideoGameDeletedEvent::class.java
        )

        `when`(
            catalogRepository.existsById(event.videoGameId)
        ).thenReturn(true)

        handler.handle(event)

        verify(catalogRepository).deleteById(
            event.videoGameId
        )
    }
}
