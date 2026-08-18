package com.vgs.ordermanagement.model.views

import com.vgs.ordermanagement.model.common.Money
import com.vgs.ordermanagement.model.common.UserId
import com.vgs.ordermanagement.model.common.VideoGameId
import com.vgs.ordermanagement.model.common.VideoGameTitle
import jakarta.persistence.AttributeOverride
import jakarta.persistence.Column
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.ZonedDateTime

@Entity
@Table(name = "catalog")
data class CatalogView (
    @Id
    @AttributeOverride(name = "value", column = Column(name = "id"))
    val id: VideoGameId,

    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "title"))
    val title: VideoGameTitle,

    val price: Money,

    val updatedAt: ZonedDateTime,

    val capacity: Int,

    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "user_id"))
    val userId: UserId
)