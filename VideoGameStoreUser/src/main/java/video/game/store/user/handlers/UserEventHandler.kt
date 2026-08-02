package video.game.store.user.handlers

import org.axonframework.eventhandling.EventHandler
import org.springframework.stereotype.Component
import video.game.store.user.model.events.UserRegisteredEvent
import video.game.store.user.model.events.UserProfileUpdatedEvent
import video.game.store.user.model.events.UserDeletedEvent
import video.game.store.user.model.events.RoleAssignedEvent
import video.game.store.user.model.events.OrdersHistoryDeletedEvent
import video.game.store.user.model.views.VideoGameStoreUserView
import video.game.store.user.repositories.VideoGameStoreUserViewJpaRepository

@Component
class UserEventHandler(val userViewRepository: VideoGameStoreUserViewJpaRepository) {

    @EventHandler
    fun on(event: UserRegisteredEvent) {
        val view = VideoGameStoreUserView(
            id = event.videoGameStoreUserId,
            email = event.email,
            password = event.password,
            fullName = event.fullname,
            shippingAddress = event.shippingAddress,
            age = event.age,
            gender = event.gender,
            role = video.game.store.user.model.enums.Role.CUSTOMER
        )
        userViewRepository.save(view)
    }

    @EventHandler
    fun on(event: UserProfileUpdatedEvent) {
        val id = event.videoGameStoreUserId
        val existing = userViewRepository.findById(id)
        if (existing.isPresent) {
            val v = existing.get().copy(
                fullName = event.fullname,
                email = event.email,
                password = event.password,
                shippingAddress = event.shippingAddress,
                age = event.age,
                gender = event.gender
            )
            userViewRepository.save(v)
        }
    }

    @EventHandler
    fun on(event: UserDeletedEvent) {
        val id = event.videoGameStoreUserId
        if (userViewRepository.existsById(id)) {
            userViewRepository.deleteById(id)
        }
    }

    @EventHandler
    fun on(event: OrdersHistoryDeletedEvent) {
        // TO DO
    }

    @EventHandler
    fun on(event: RoleAssignedEvent) {
        val id = event.videoGameStoreUserId
        val existing = userViewRepository.findById(id)
        if (existing.isPresent) {
            val v = existing.get().copy(role = event.role)
            userViewRepository.save(v)
        }
    }
}

