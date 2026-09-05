package video.game.store.user.handlers

import org.axonframework.eventhandling.EventHandler
import org.springframework.stereotype.Component
import video.game.store.user.model.events.OrderDeletedExternalEvent
import video.game.store.user.repositories.OrderRepository

@Component
class UserExternalEventHandler(
    private val orderRepository: OrderRepository
) {

    @EventHandler
    fun on(event: OrderDeletedExternalEvent) {
        orderRepository.deleteById(event.id)
    }
}
