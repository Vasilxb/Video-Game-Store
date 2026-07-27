package com.vgs.ordermanagement.services.impl

import com.vgs.ordermanagement.repositories.EventMessagingRepository
import com.vgs.ordermanagement.services.EventMessagingService
import org.springframework.stereotype.Service

@Service
class EventMessagingServiceImpl(
    val eventMessagingRepository: EventMessagingRepository
) : EventMessagingService {
    override fun send(topic: String, key: String, payload: String) {
        eventMessagingRepository.send(topic, key, payload)
    }
}
