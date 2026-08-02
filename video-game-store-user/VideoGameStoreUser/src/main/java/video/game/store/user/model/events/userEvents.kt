package video.game.store.user.model.events

import org.axonframework.modelling.command.TargetAggregateIdentifier
import video.game.store.user.model.AssignRoleCommand
import video.game.store.user.model.DeleteOrderCommand
import video.game.store.user.model.DeleteUserAccountCommand
import video.game.store.user.model.LoginUser2MFACommand
import video.game.store.user.model.LoginUserCommand
import video.game.store.user.model.LogoutUserCommand
import video.game.store.user.model.RegisterUser2MFACommand
import video.game.store.user.model.RegisterUserCommand
import video.game.store.user.model.UpdateUserAccountCommand
import video.game.store.user.model.common.Age
import video.game.store.user.model.common.Email
import video.game.store.user.model.common.FullName
import video.game.store.user.model.common.Gender
import video.game.store.user.model.common.Password
import video.game.store.user.model.common.ShippingAddress
import video.game.store.user.model.common.VideoGameOrderId
import video.game.store.user.model.common.VideoGameStoreUserId
import video.game.store.user.model.enums.Role

data class UserRegisteredEvent(
    var videoGameStoreUserId: VideoGameStoreUserId,
    var fullname: FullName,
    var email: Email,
    var password: Password,
    var shippingAddress: ShippingAddress,
    var age: Age,
    var gender: Gender
){
    constructor(command: RegisterUserCommand): this(
        videoGameStoreUserId = command.id,
        fullname = command.fullname,
        email = command.email,
        password = command.password,
        shippingAddress = command.shippingAddress,
        age = command.age,
        gender = command.gender
    )
}

data class UserRegistered2MFAEvent(
    var videoGameStoreUserId: VideoGameStoreUserId
){
    constructor(command: RegisterUser2MFACommand): this(
        videoGameStoreUserId =  command.id
    )
}

data class UserLoggedInEvent(
    var videoGameStoreUserId: VideoGameStoreUserId,
    var email: Email,
    var password: Password
){
    constructor(command: LoginUserCommand): this(
        videoGameStoreUserId = command.id,
        email = command.email,
        password = command.password
    )
}

data class UserLoggedIn2MFAEvent(
    var videoGameStoreUserId: VideoGameStoreUserId
){
    constructor(command: LoginUser2MFACommand): this(
        videoGameStoreUserId = command.id
    )
}

data class UserLoggedOutEvent(
    var videoGameStoreUserId: VideoGameStoreUserId
){
    constructor(command: LogoutUserCommand): this(
        videoGameStoreUserId = command.id
    )
}

data class UserDeletedEvent(
    var videoGameStoreUserId: VideoGameStoreUserId
){
    constructor(command: DeleteUserAccountCommand): this(
        videoGameStoreUserId = command.id
    )
}

data class OrdersHistoryDeletedEvent(
    var videoGameStoreUserId: VideoGameStoreUserId
)

data class OrderDeletedEvent(
    var videoGameStoreUserId: VideoGameStoreUserId,
    var videoGameOrderId: VideoGameOrderId
){
    constructor(command: DeleteOrderCommand): this(
        videoGameStoreUserId = command.id,
        videoGameOrderId = command.videoGameOrderId
    )
}

data class UserProfileUpdatedEvent(
    var videoGameStoreUserId: VideoGameStoreUserId,
    var fullname: FullName,
    var email: Email,
    var password: Password,
    var shippingAddress: ShippingAddress,
    var age: Age,
    var gender: Gender
){
    constructor(command: UpdateUserAccountCommand): this(
        videoGameStoreUserId = command.id,
        fullname = command.fullname,
        email = command.email,
        password = command.password,
        shippingAddress = command.shippingAddress,
        age = command.age,
        gender = command.gender
    )
}

data class RoleAssignedEvent(
    var videoGameStoreUserId: VideoGameStoreUserId,
    var role: Role
){
    constructor(command: AssignRoleCommand): this(
        videoGameStoreUserId = command.id,
        role = command.role
    )
}
