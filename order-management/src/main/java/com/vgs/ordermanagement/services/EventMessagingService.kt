package com.vgs.ordermanagement.services

interface EventMessagingService {
    fun send(topic: String, key: String, payload: String)
}
