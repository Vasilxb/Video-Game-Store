package video.game.store.user.services.impl

import org.springframework.stereotype.Service
import video.game.store.user.model.common.VideoGameStoreUserId
import video.game.store.user.model.common.Email
import video.game.store.user.model.exceptions.UserNotFoundException
import video.game.store.user.model.views.VideoGameStoreUserView
import video.game.store.user.repositories.VideoGameStoreUserViewJpaRepository
import video.game.store.user.services.UserViewReadService

@Service
class UserViewReadServiceImpl(
    val userViewJpaRepository: VideoGameStoreUserViewJpaRepository
) : UserViewReadService {

    override fun findById(userId: VideoGameStoreUserId): VideoGameStoreUserView {
        return userViewJpaRepository.findById(userId)
            .orElseThrow { UserNotFoundException(userId) }
    }

    override fun findAll(): List<VideoGameStoreUserView> = userViewJpaRepository.findAll()

    override fun findByEmail(email: String): VideoGameStoreUserView? =
        userViewJpaRepository.findByEmail(Email(email))
}

