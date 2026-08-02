package video.game.store.user.repositories

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import video.game.store.user.model.common.Email
import video.game.store.user.model.common.VideoGameStoreUserId
import video.game.store.user.model.views.VideoGameStoreUserView

@Repository
interface VideoGameStoreUserViewJpaRepository : JpaRepository<VideoGameStoreUserView, VideoGameStoreUserId> {
    fun findByEmail(email: Email): VideoGameStoreUserView?
}

