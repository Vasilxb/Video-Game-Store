package com.vgs.catalog.model.views

import com.vgs.catalog.model.common.Identifier
import com.vgs.catalog.model.common.LabeledEntity
import com.vgs.catalog.model.common.Money
import com.vgs.catalog.model.common.UserId
import com.vgs.catalog.model.common.VideoGameId
import com.vgs.catalog.model.enums.Platform
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
@Table(name = "video_games")
@Immutable
data class VideoGameView(

    @Id
    @AttributeOverride(
        name = "value",
        column = Column(name = "id")
    )
    val id: VideoGameId,

    val updatedAt: ZonedDateTime,

    val name: String,

    val price: Money,

    @Enumerated(EnumType.STRING)
    val platform: Platform,

    val year: Int,

    val studio: String,

    val rating: Double,

    @Embedded
    @AttributeOverride(
        name = "value",
        column = Column(name = "store_id")
    )
    val storeId: UserId,

    val capacity: Int

) : LabeledEntity {

    override fun getId(): Identifier {
        return id
    }

    override fun getLabel(): String {
        return "Video Game: $id"
    }
}