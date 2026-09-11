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
import com.vgs.ordermanagement.model.common.OrderId
import com.vgs.ordermanagement.model.events.OrderDeletedExternalEvent
import org.junit.jupiter.api.TestTemplate
import org.junit.jupiter.api.extension.ExtendWith

@Provider("order-management")
@Consumer("users-service")
@PactFolder("pacts")
class OrderDeletedEventProducerTest {

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

    @PactVerifyProvider("Order Deleted Event")
    fun verifyOrderDeletedEvent(): MessageAndMetadata {

        val event = OrderDeletedExternalEvent(
            id = OrderId(
                "Order:550e8400-e29b-41d4-a716-446655440000"
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
