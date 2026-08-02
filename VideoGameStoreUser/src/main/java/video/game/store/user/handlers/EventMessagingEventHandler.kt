package video.game.store.user.handlers

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import org.axonframework.eventhandling.EventHandler
import org.springframework.stereotype.Component
import video.game.store.user.model.events.AbstractEvent
import video.game.store.user.services.EventMessagingService

@Component
class EventMessagingEventHandler(val eventMessagingService: EventMessagingService) {

    private val objectMapper = ObjectMapper()
        .registerModule(JavaTimeModule())
        .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)

    @EventHandler
    fun on(event: AbstractEvent) {
        val externalEvent = event.toExternalEvent() ?: return

        val eventJSON = objectMapper.writeValueAsString(externalEvent)

        eventMessagingService.send(
            topic = event.eventTopic(),
            key = event.identifier.value,
            payload = eventJSON
        )
    }
}
