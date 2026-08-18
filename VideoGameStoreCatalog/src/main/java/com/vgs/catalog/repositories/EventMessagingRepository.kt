package com.vgs.catalog.repositories

interface EventMessagingRepository {

    fun send(
        topic: String,
        key: String,
        payload: String
    )
}