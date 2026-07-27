package com.vgs.ordermanagement.model.views

import com.vgs.ordermanagement.model.common.Identifier
import com.vgs.ordermanagement.model.common.LabeledEntity
import com.vgs.ordermanagement.model.common.Money
import com.vgs.ordermanagement.model.common.OrderId
import com.vgs.ordermanagement.model.common.UserId
import com.vgs.ordermanagement.model.common.VideoGameId
import com.vgs.ordermanagement.model.enums.OrderStatus
import jakarta.persistence.AttributeOverride
import jakarta.persistence.Column
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.Immutable
import java.time.ZonedDateTime

@Entity
@Table(name = "orders")
@Immutable
data class OrderView (
    @Id
    @AttributeOverride(name = "value", column = Column(name = "id"))
    val id: OrderId,

    val updatedAt: ZonedDateTime,

    @Enumerated(EnumType.STRING)
    val status: OrderStatus,

    val amount: Money,

    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "video_game_id"))
    val videoGameId: VideoGameId,

    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "user_id"))
    val userId: UserId
) : LabeledEntity {
    override fun getId(): Identifier<out Any> {
        return id
    }

    override fun getLabel(): String {
        return "Order: $id"
    }
}