package video.game.store.user.services

import video.game.store.user.model.common.VideoGameStoreUserId
import video.game.store.user.model.views.VideoGameStoreUserView

interface UserViewReadService {
    fun findAll(): List<VideoGameStoreUserView>
    fun findById(userId: VideoGameStoreUserId): VideoGameStoreUserView
    fun findByEmail(email: String): VideoGameStoreUserView?
}

