package video.game.store.user.model.views

import jakarta.persistence.AttributeOverride
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.Immutable
import video.game.store.user.model.common.VideoGameOrderId

@Entity
@Table(name = "orders")
@Immutable
data class OrderView (
	@Id
	@AttributeOverride(name = "value", column = Column(name = "id"))
	val id: VideoGameOrderId,
	)