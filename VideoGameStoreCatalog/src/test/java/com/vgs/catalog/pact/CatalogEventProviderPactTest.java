package com.vgs.catalog.pact;

import au.com.dius.pact.provider.MessageAndMetadata;
import au.com.dius.pact.provider.PactVerifyProvider;
import au.com.dius.pact.provider.junit.Consumer;
import au.com.dius.pact.provider.junit.Provider;
import au.com.dius.pact.provider.junit.loader.PactFolder;
import au.com.dius.pact.provider.junit5.AmpqTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import com.vgs.catalog.model.common.Money;
import com.vgs.catalog.model.common.UserId;
import com.vgs.catalog.model.events.ExternalVideoGameId;
import com.vgs.catalog.model.events.VideoGameCapacityChangedExternalEvent;
import com.vgs.catalog.model.events.VideoGameCreatedExternalEvent;
import com.vgs.catalog.model.events.VideoGameDeletedExternalEvent;
import com.vgs.catalog.model.events.VideoGameUpdatedExternalEvent;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Map;

@Provider("catalog-service")
@Consumer("order-management")
@PactFolder("pacts")
public class CatalogEventProviderPactTest {

    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    @BeforeEach
    void before(PactVerificationContext context) {
        context.setTarget(new AmpqTestTarget());
    }

    @TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider.class)
    void pactVerificationTestTemplate(PactVerificationContext context) {
        context.verifyInteraction();
    }

    @PactVerifyProvider("VideoGameCreatedEvent when a video game is added")
    public MessageAndMetadata verifyVideoGameCreated()
            throws JsonProcessingException {

        VideoGameCreatedExternalEvent event =
                new VideoGameCreatedExternalEvent(
                        new ExternalVideoGameId(
                                "VideoGame:550e8400-e29b-41d4-a716-446655440000"
                        ),
                        "The Witcher 3",
                        new Money(
                                new BigDecimal("59.99"),
                                "EUR"
                        ),
                        new UserId(
                                "VideoGameStoreUser:550e8400-e29b-41d4-a716-446655440001"
                        ),
                        ZonedDateTime.parse(
                                "2024-01-01T10:15:30+01:00[Europe/Warsaw]"
                        ),
                        10
                );

        return message(event);
    }

    @PactVerifyProvider("VideoGameUpdatedEvent when a video game is updated")
    public MessageAndMetadata verifyVideoGameUpdated()
            throws JsonProcessingException {

        VideoGameUpdatedExternalEvent event =
                new VideoGameUpdatedExternalEvent(
                        new ExternalVideoGameId(
                                "VideoGame:550e8400-e29b-41d4-a716-446655440000"
                        ),
                        "The Witcher 3 Updated",
                        new Money(
                                new BigDecimal("49.99"),
                                "EUR"
                        ),
                        new UserId(
                                "VideoGameStoreUser:550e8400-e29b-41d4-a716-446655440001"
                        ),
                        ZonedDateTime.parse(
                                "2024-01-02T10:15:30+01:00[Europe/Warsaw]"
                        ),
                        20
                );

        return message(event);
    }

    @PactVerifyProvider("VideoGameDeletedEvent when a video game is deleted")
    public MessageAndMetadata verifyVideoGameDeleted()
            throws JsonProcessingException {

        VideoGameDeletedExternalEvent event =
                new VideoGameDeletedExternalEvent(
                        new ExternalVideoGameId(
                                "VideoGame:550e8400-e29b-41d4-a716-446655440000"
                        )
                );

        return message(event);
    }

    @PactVerifyProvider("VideoGameCapacityChangedEvent when capacity changes")
    public MessageAndMetadata verifyCapacityChanged()
            throws JsonProcessingException {

        VideoGameCapacityChangedExternalEvent event =
                new VideoGameCapacityChangedExternalEvent(
                        new ExternalVideoGameId(
                                "VideoGame:550e8400-e29b-41d4-a716-446655440000"
                        ),
                        5,
                        ZonedDateTime.parse(
                                "2024-01-03T10:15:30+01:00[Europe/Warsaw]"
                        )
                );
        return message(event);
    }

    private MessageAndMetadata message(Object event)
            throws JsonProcessingException {

        return new MessageAndMetadata(
                objectMapper.writeValueAsBytes(event),
                Map.of(
                        "Content-Type",
                        "application/json; charset=UTF-8"
                )
        );
    }
}