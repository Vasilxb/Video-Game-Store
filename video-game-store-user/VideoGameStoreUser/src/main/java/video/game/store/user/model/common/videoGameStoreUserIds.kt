package video.game.store.user.model.common

import jakarta.persistence.Embeddable
import video.game.store.user.model.VideoGameStoreUser
import java.util.*
import kotlin.jvm.java

@Embeddable
open class VideoGameStoreUserId(value: String) : Identifier<VideoGameStoreUser>(value, VideoGameStoreUser::class.java) {
    constructor() : this(UUID.randomUUID().toString())

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        return this.value == (other as VideoGameStoreUserId).value
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }
}
