package video.game.store.user.model
import org.axonframework.modelling.command.TargetAggregateIdentifier
import video.game.store.user.model.common.Age
import video.game.store.user.model.common.Email
import video.game.store.user.model.common.FullName
import video.game.store.user.model.common.Gender
import video.game.store.user.model.common.Password
import video.game.store.user.model.common.ShippingAddress
import video.game.store.user.model.common.VideoGameOrderId
import video.game.store.user.model.common.VideoGameStoreUserId
import video.game.store.user.model.enums.Role

data class RegisterUserCommand(
    @TargetAggregateIdentifier
    var id: VideoGameStoreUserId,
    var fullname: FullName,
    var email: Email,
    var password: Password,
    var shippingAddress: ShippingAddress,
    var age: Age,
    var gender: Gender
)

data class RegisterUser2MFACommand(
    @TargetAggregateIdentifier
    var id: VideoGameStoreUserId
)

data class LoginUserCommand(
    @TargetAggregateIdentifier
    var id: VideoGameStoreUserId,
    var email: Email,
    var password: Password
)

data class LoginUser2MFACommand(
    @TargetAggregateIdentifier
    var id: VideoGameStoreUserId
)

data class LogoutUserCommand(
    @TargetAggregateIdentifier
    var id: VideoGameStoreUserId
)

data class DeleteUserAccountCommand(
    @TargetAggregateIdentifier
    var id: VideoGameStoreUserId
)

data class DeleteOrderCommand(
    @TargetAggregateIdentifier
    var id: VideoGameStoreUserId,
    var videoGameOrderId : VideoGameOrderId
)

data class UpdateUserAccountCommand(
    @TargetAggregateIdentifier
    var id: VideoGameStoreUserId,
    var fullname: FullName,
    var email: Email,
    var password: Password,
    var shippingAddress: ShippingAddress,
    var age: Age,
    var gender: Gender
)

data class AssignRoleCommand(
    @TargetAggregateIdentifier
    var id: VideoGameStoreUserId,
    var role: Role
)

