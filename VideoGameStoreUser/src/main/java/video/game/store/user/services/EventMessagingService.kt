package video.game.store.user.services

interface EventMessagingService {
    fun send(topic: String, key: String, payload: String)
}
