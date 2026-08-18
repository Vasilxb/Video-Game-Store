package com.vgs.catalog.services

interface EventMessagingService {
    fun send(
        topic: String,
        key: String,
        payload: String
    )
}