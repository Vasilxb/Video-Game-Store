package video.game.store.user.repositories

import org.springframework.data.jpa.repository.JpaRepository
import video.game.store.user.model.common.VideoGameOrderId
import video.game.store.user.model.views.OrderView

interface OrderRepository : JpaRepository<OrderView, VideoGameOrderId> {
}