package video.game.store.user.repositories

interface EventMessagingRepository {
    fun send(topic: String, key: String, payload: String)
}
