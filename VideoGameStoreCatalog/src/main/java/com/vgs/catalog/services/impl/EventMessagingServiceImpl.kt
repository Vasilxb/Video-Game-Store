package com.vgs.catalog.services.impl

import com.vgs.catalog.repositories.EventMessagingRepository
import com.vgs.catalog.services.EventMessagingService
import org.springframework.stereotype.Service

@Service
class EventMessagingServiceImpl(
    val eventMessagingRepository: EventMessagingRepository
) : EventMessagingService {

    override fun send(
        topic: String,
        key: String,
        payload: String
    ) {
        eventMessagingRepository.send(
            topic,
            key,
            payload
        )
    }
}