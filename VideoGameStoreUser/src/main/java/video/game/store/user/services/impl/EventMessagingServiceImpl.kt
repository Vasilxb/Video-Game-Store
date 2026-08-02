package video.game.store.user.services.impl

import video.game.store.user.repositories.EventMessagingRepository
import video.game.store.user.services.EventMessagingService
import org.springframework.stereotype.Service

@Service
class EventMessagingServiceImpl(
    val eventMessagingRepository: EventMessagingRepository
) : EventMessagingService {
    override fun send(topic: String, key: String, payload: String) {
        eventMessagingRepository.send(topic, key, payload)
    }
}