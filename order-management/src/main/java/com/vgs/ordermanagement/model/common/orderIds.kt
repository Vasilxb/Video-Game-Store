package com.vgs.ordermanagement.model.common

import com.vgs.ordermanagement.model.Order
import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import java.util.UUID

@Embeddable
open class OrderId(value: String) : Identifier<Order>(value, Order
::class.java) {
    constructor() : this(UUID.randomUUID().toString())

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        return this.value == (other as OrderId).value
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }
}

@Embeddable
data class VideoGameId(
    @Column(name = "video_game_id")
    val value: String
) {
    constructor() : this("VideoGame:" + UUID.randomUUID().toString())

    init {
        require(value.startsWith("VideoGame:")) { "VideoGameId must start with 'VideoGame:'" }

        val uuidPart = value.substringAfter("VideoGame:", "")
        require(uuidPart.length == 36) { "Invalid VideoGameId format: UUID must be 36 characters long" }
    }

    override fun toString(): String = value
}

@Embeddable
data class UserId(
    @Column(name = "user_id")
    val value: String
) {
    constructor() : this("VideoGameStoreUser:" + UUID.randomUUID().toString())

    init {
        require(value.startsWith("VideoGameStoreUser:")) { "UserId must start with 'VideoGameStoreUser:'" }

        val uuidPart = value.substringAfter("VideoGameStoreUser:", "")
        require(uuidPart.length == 36) { "Invalid UserId format: UUID must be 36 characters long" }
    }

    override fun toString(): String = value
}
