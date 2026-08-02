package video.game.store.user.model

import video.game.store.user.model.common.Age
import video.game.store.user.model.common.Email
import video.game.store.user.model.common.FullName
import video.game.store.user.model.common.Gender
import video.game.store.user.model.common.Password
import video.game.store.user.model.common.ShippingAddress
import video.game.store.user.model.common.VideoGameOrderId
import video.game.store.user.model.common.VideoGameStoreUserId
import video.game.store.user.model.enums.Role

data class RegisterUserCommandDto(
    val fullname: FullName,
    val email: Email,
    val password: Password,
    val shippingAddress: ShippingAddress,
    val age: Age,
    val gender: Gender
)

data class UpdateUserAccountCommandDto(
    val videoGameStoreUserId: VideoGameStoreUserId,
    val fullname: FullName,
    val email: Email,
    val password: Password,
    val shippingAddress: ShippingAddress,
    val age: Age,
    val gender: Gender
)

data class LoginUserCommandDto(
    val videoGameStoreUserId: VideoGameStoreUserId,
    val email: Email,
    val password: Password
)

data class RegisterUser2MFACommandDto(
    val videoGameStoreUserId: VideoGameStoreUserId
)

data class LoginUser2MFACommandDto(
    val videoGameStoreUserId: VideoGameStoreUserId
)

data class LogoutUserCommandDto(
    val videoGameStoreUserId: VideoGameStoreUserId
)

data class DeleteOrderCommandDto(
    val videoGameStoreUserId: VideoGameStoreUserId,
    val videoGameOrderId: VideoGameOrderId
)

data class AssignRoleCommandDto(
    val videoGameStoreUserId: VideoGameStoreUserId,
    val role: Role
)

