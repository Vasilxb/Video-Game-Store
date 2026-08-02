package video.game.store.user.model.common

import jakarta.persistence.Column
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

@Embeddable
data class VideoGameOrderId(
    @Column(name = "video_game_order_id")
    val value: String
) {
    constructor() : this("Video Game Order:" + UUID.randomUUID().toString())

    init {
        require(value.startsWith("Video Game:")) { "VideoGameOrderId must start with 'Video Game Order:'" }

        val uuidPart = value.substringAfter("Video Game:", "")
        require(uuidPart.length == 36) { "Invalid VideoGameOrderId format: UUID must be 36 characters long" }
    }

    override fun toString(): String = value
}
