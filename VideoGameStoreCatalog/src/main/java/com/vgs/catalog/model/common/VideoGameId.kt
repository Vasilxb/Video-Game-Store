package com.vgs.catalog.model.common

import com.vgs.catalog.model.VideoGame
import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import java.util.UUID

@Embeddable
open class VideoGameId(value: String) :
    Identifier(value, VideoGame::class.java) {

    constructor() : this(UUID.randomUUID().toString())

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        other as VideoGameId

        return this.value == other.value
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }
}