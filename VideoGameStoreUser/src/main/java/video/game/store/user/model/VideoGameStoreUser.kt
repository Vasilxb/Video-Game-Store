package video.game.store.user.model

import jakarta.persistence.AttributeOverride
import jakarta.persistence.Column
import jakarta.persistence.Embedded
import jakarta.persistence.EmbeddedId
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.modelling.command.AggregateLifecycle
import org.axonframework.spring.stereotype.Aggregate
import video.game.store.user.model.common.Age
import video.game.store.user.model.common.Email
import video.game.store.user.model.common.FullName
import video.game.store.user.model.common.Gender
import video.game.store.user.model.common.Identifier
import video.game.store.user.model.common.LabeledEntity
import video.game.store.user.model.common.Password
import video.game.store.user.model.common.ShippingAddress
import video.game.store.user.model.common.VideoGameOrderId
import video.game.store.user.model.common.VideoGameStoreUserId
import video.game.store.user.model.enums.Role
import video.game.store.user.model.events.UserRegisteredEvent
import video.game.store.user.model.events.UserProfileUpdatedEvent
import video.game.store.user.model.events.RoleAssignedEvent
import video.game.store.user.model.events.UserDeletedEvent
import video.game.store.user.model.events.UserRegistered2MFAEvent
import video.game.store.user.model.events.UserLoggedInEvent
import video.game.store.user.model.events.UserLoggedIn2MFAEvent
import video.game.store.user.model.events.UserLoggedOutEvent
import video.game.store.user.model.events.OrderDeletedEvent

@Aggregate
class VideoGameStoreUser : LabeledEntity {
    @AggregateIdentifier
    @EmbeddedId
    @AttributeOverride(name = "value", column = Column(name = "id"))
    private lateinit var id: VideoGameStoreUserId
    private lateinit var email: Email
    private lateinit var password: Password
    private lateinit var fullName: FullName
    private lateinit var shippingAddress: ShippingAddress
    private lateinit var age: Age
    private lateinit var gender: Gender
    private lateinit var role: Role
    private var mfaVerified: Boolean = false
    private var loggedIn: Boolean = false
    private var deleted: Boolean = false
    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "video_game_id"))
    private lateinit var videoGameOrderId: VideoGameOrderId

    @CommandHandler
    constructor(command: RegisterUserCommand) {
        val event = UserRegisteredEvent(
            videoGameStoreUserId = command.id,
            fullname = command.fullname,
            email = command.email,
            password = command.password,
            shippingAddress = command.shippingAddress,
            age = command.age,
            gender = command.gender
        )
        this.on(event)
        AggregateLifecycle.apply(event)
    }

    fun on(event: UserRegisteredEvent) {
        this.id = event.videoGameStoreUserId
        this.email = event.email
        this.password = event.password
        this.fullName = event.fullname
        this.shippingAddress = event.shippingAddress
        this.age = event.age
        this.gender = event.gender
        this.role = Role.CUSTOMER
    }

    @CommandHandler
    fun register2MFA(command: RegisterUser2MFACommand) {
        val event = UserRegistered2MFAEvent(command)
        this.on(event)
        AggregateLifecycle.apply(event)
    }

    fun on(event: UserRegistered2MFAEvent) {
        this.mfaVerified = true
    }

    @CommandHandler
    fun login(command: LoginUserCommand) {
        val event = UserLoggedInEvent(command)
        this.on(event)
        AggregateLifecycle.apply(event)
    }

    fun on(event: UserLoggedInEvent) {
        this.loggedIn = true
    }

    @CommandHandler
    fun login2MFA(command: LoginUser2MFACommand) {
        val event = UserLoggedIn2MFAEvent(command)
        this.on(event)
        AggregateLifecycle.apply(event)
    }

    fun on(event: UserLoggedIn2MFAEvent) {
        this.loggedIn = true
    }

    @CommandHandler
    fun logout(command: LogoutUserCommand) {
        val event = UserLoggedOutEvent(command)
        this.on(event)
        AggregateLifecycle.apply(event)
    }

    fun on(event: UserLoggedOutEvent) {
        this.loggedIn = false
    }

    @CommandHandler
    fun updateAccount(command: UpdateUserAccountCommand) {
        val event = UserProfileUpdatedEvent(command)
        this.on(event)
        AggregateLifecycle.apply(event)
    }

    fun on(event: UserProfileUpdatedEvent) {
        this.fullName = event.fullname
        this.email = event.email
        this.password = event.password
        this.shippingAddress = event.shippingAddress
        this.age = event.age
        this.gender = event.gender
    }

    @CommandHandler
    fun deleteAccount(command: DeleteUserAccountCommand) {
        val event = UserDeletedEvent(command)
        this.on(event)
        AggregateLifecycle.apply(event)
    }

    fun on(event: UserDeletedEvent) {
        this.deleted = true
    }

    @CommandHandler
    fun deleteOrder(command: DeleteOrderCommand) {
        val event = OrderDeletedEvent(command)
        AggregateLifecycle.apply(event)
    }

    @CommandHandler
    fun assignRole(command: AssignRoleCommand) {
        val event = RoleAssignedEvent(command)
        this.on(event)
        AggregateLifecycle.apply(event)
    }

    fun on(event: RoleAssignedEvent) {
        this.role = event.role
    }

    override fun getId(): Identifier<out Any> {
        return this.id
    }

    override fun getLabel(): FullName {
        return this.fullName
    }
}