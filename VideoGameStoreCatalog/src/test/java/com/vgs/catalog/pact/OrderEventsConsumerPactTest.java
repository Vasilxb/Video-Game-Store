package com.vgs.catalog.pact;

import au.com.dius.pact.consumer.MessagePactBuilder;
import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.consumer.junit5.ProviderType;
import au.com.dius.pact.model.PactSpecVersion;
import au.com.dius.pact.model.v3.messaging.Message;
import au.com.dius.pact.model.v3.messaging.MessagePact;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.kotlin.KotlinModule;
import com.vgs.catalog.handlers.OrderExternalEventHandler;
import com.vgs.catalog.model.DecreaseVideoGameCapacityCommand;
import com.vgs.catalog.model.IncreaseVideoGameCapacityCommand;

import org.axonframework.commandhandling.gateway.CommandGateway;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.Mockito.verify;


@ExtendWith({
        PactConsumerTestExt.class,
        MockitoExtension.class
})
@PactTestFor(
        providerName = "order-management",
        providerType = ProviderType.ASYNCH,
        pactVersion = PactSpecVersion.V3
)
public class OrderEventsConsumerPactTest {

    private static final String GAME_ID =
            "VideoGame:550e8400-e29b-41d4-a716-446655440000";

    @Mock
    private CommandGateway commandGateway;

    private OrderExternalEventHandler handler;


    @BeforeEach
    void setup() {
        ObjectMapper objectMapper = new ObjectMapper()
                .registerModule(new KotlinModule.Builder().build());

        handler = new OrderExternalEventHandler(
                commandGateway,
                objectMapper
        );
    }


    @Pact(consumer = "catalog-service")
    public MessagePact orderCreatedPact(
            MessagePactBuilder builder
    ) {
        PactDslJsonBody body = new PactDslJsonBody();

        body.object("videoGameId")
                .stringMatcher(
                        "value",
                        "VideoGame:[0-9a-fA-F-]{36}",
                        GAME_ID
                )
                .closeObject();

        return builder
                .expectsToReceive(
                        "OrderCreatedEvent when an order is created"
                )
                .withMetadata(metadata())
                .withContent(body)
                .toPact();
    }


    @Test
    @PactTestFor(
            pactMethod = "orderCreatedPact",
            providerType = ProviderType.ASYNCH
    )
    void handlesOrderCreated(
            List<Message> messages
    ) {
        handler.handleOrderCreated(
                messageBody(messages)
        );

        ArgumentCaptor<Object> captor =
                ArgumentCaptor.forClass(Object.class);

        verify(commandGateway)
                .sendAndWait(captor.capture());

        Object command = captor.getValue();

        DecreaseVideoGameCapacityCommand decrease =
                assertInstanceOf(
                        DecreaseVideoGameCapacityCommand.class,
                        command
                );

        assertEquals(1, decrease.getAmount());

        assertEquals(
                GAME_ID,
                decrease.getId().getValue()
        );
    }


    @Pact(consumer = "catalog-service")
    public MessagePact orderCancelledPact(
            MessagePactBuilder builder
    ) {
        PactDslJsonBody body = new PactDslJsonBody();

        body.object("videoGameId")
                .stringMatcher(
                        "value",
                        "VideoGame:[0-9a-fA-F-]{36}",
                        GAME_ID
                )
                .closeObject();

        body.stringValue(
                "status",
                "CANCELLED"
        );

        return builder
                .expectsToReceive(
                        "OrderStatusUpdatedEvent when an order is cancelled"
                )
                .withMetadata(metadata())
                .withContent(body)
                .toPact();
    }


    @Test
    @PactTestFor(
            pactMethod = "orderCancelledPact",
            providerType = ProviderType.ASYNCH
    )
    void handlesCancelledOrder(
            List<Message> messages
    ) {
        handler.handleOrderStatusUpdated(
                messageBody(messages)
        );

        ArgumentCaptor<Object> captor =
                ArgumentCaptor.forClass(Object.class);

        verify(commandGateway)
                .sendAndWait(captor.capture());

        Object command = captor.getValue();

        IncreaseVideoGameCapacityCommand increase =
                assertInstanceOf(
                        IncreaseVideoGameCapacityCommand.class,
                        command
                );

        assertEquals(1, increase.getAmount());

        assertEquals(
                GAME_ID,
                increase.getId().getValue()
        );
    }


    private static String messageBody(
            List<Message> messages
    ) {
        return new String(
                messages.get(0).contentsAsBytes(),
                StandardCharsets.UTF_8
        );
    }


    private static Map<String, String> metadata() {
        Map<String, String> metadata = new HashMap<>();
        metadata.put(
                "Content-Type",
                "application/json; charset=UTF-8"
        );
        return metadata;
    }
}