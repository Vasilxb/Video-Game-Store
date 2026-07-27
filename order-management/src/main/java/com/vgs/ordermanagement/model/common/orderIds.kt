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
    constructor() : this("Video Game:" + UUID.randomUUID().toString())

    init {
        require(value.startsWith("Video Game:")) { "VideoGameId must start with 'Video Game:'" }

        val uuidPart = value.substringAfter("Video Game:", "")
        require(uuidPart.length == 36) { "Invalid VideoGameId format: UUID must be 36 characters long" }
    }

    override fun toString(): String = value
}

@Embeddable
data class UserId(
    @Column(name = "user_id")
    val value: String
) {
    constructor() : this("User:" + UUID.randomUUID().toString())

    init {
        require(value.startsWith("User:")) { "UserId must start with 'User:'" }

        val uuidPart = value.substringAfter("User:", "")
        require(uuidPart.length == 36) { "Invalid UserId format: UUID must be 36 characters long" }
    }

    override fun toString(): String = value
}
