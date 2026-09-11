package com.vgs.ordermanagement.contract

import au.com.dius.pact.provider.MessageAndMetadata
import au.com.dius.pact.provider.PactVerifyProvider
import au.com.dius.pact.provider.junit.Consumer
import au.com.dius.pact.provider.junit.Provider
import au.com.dius.pact.provider.junit.loader.PactFolder
import au.com.dius.pact.provider.junit5.PactVerificationContext
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.vgs.ordermanagement.model.common.Money
import com.vgs.ordermanagement.model.common.OrderId
import com.vgs.ordermanagement.model.common.UserId
import com.vgs.ordermanagement.model.common.VideoGameId
import com.vgs.ordermanagement.model.enums.OrderStatus
import com.vgs.ordermanagement.model.events.OrderCreatedExternalEvent
import org.junit.jupiter.api.TestTemplate
import org.junit.jupiter.api.extension.ExtendWith
import java.math.BigDecimal
import java.time.ZonedDateTime

@Provider("order-management")
@Consumer("catalog-service")
@PactFolder("pacts")
class OrderCreatedEventProducerTest {

    private val objectMapper = ObjectMapper()
        .registerModule(KotlinModule.Builder().build())
        .registerModule(JavaTimeModule())
        .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)


    @TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider::class)
    fun pactVerificationTestTemplate(
        context: PactVerificationContext
    ) {
        context.verifyInteraction()
    }

    @PactVerifyProvider("Order Created Event")
    fun verifyOrderCreatedEvent(): MessageAndMetadata {

        val event = OrderCreatedExternalEvent(
            id = OrderId(
                "Order:550e8400-e29b-41d4-a716-446655440000"
            ),
            updatedAt = ZonedDateTime.parse(
                "2024-01-01T10:15:30+01:00[Europe/Warsaw]"
            ),
            amount = Money(
                BigDecimal("59.99"),
                "EUR"
            ),
            status = OrderStatus.PROCESSING,
            userId = UserId(
                "VideoGameStoreUser:550e8400-e29b-41d4-a716-446655440001"
            ),
            videoGameId = VideoGameId(
                "VideoGame:550e8400-e29b-41d4-a716-446655440000"
            )
        )

        return MessageAndMetadata(
            objectMapper
                .writeValueAsString(event)
                .toByteArray(),
            mapOf(
                "contentType" to "application/json"
            )
        )
    }
}
