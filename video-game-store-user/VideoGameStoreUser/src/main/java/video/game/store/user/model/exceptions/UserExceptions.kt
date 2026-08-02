package video.game.store.user.model.exceptions

import video.game.store.user.model.common.VideoGameStoreUserId

class UserNotFoundException(userId: VideoGameStoreUserId)
    : RuntimeException("User with id $userId not found")

class UserAlreadyExistsException(email: String)
    : RuntimeException("User with email $email already exists")

