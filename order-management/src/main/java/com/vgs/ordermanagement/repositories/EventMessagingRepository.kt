package com.vgs.ordermanagement.repositories

interface EventMessagingRepository {
    fun send(topic: String, key: String, payload: String)
}
