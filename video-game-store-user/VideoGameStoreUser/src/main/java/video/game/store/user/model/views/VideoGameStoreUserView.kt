package video.game.store.user.model.views

import jakarta.persistence.Embedded
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Table
import jakarta.persistence.AttributeOverride
import jakarta.persistence.Column
import org.hibernate.annotations.Immutable
import video.game.store.user.model.common.Age
import video.game.store.user.model.common.Email
import video.game.store.user.model.common.FullName
import video.game.store.user.model.common.Gender
import video.game.store.user.model.common.Identifier
import video.game.store.user.model.common.LabeledEntity
import video.game.store.user.model.common.Password
import video.game.store.user.model.common.ShippingAddress
import video.game.store.user.model.common.VideoGameStoreUserId
import video.game.store.user.model.enums.Role

@Entity
@Table(name = "video_game_store_user")
@Immutable
data class VideoGameStoreUserView(
    @EmbeddedId
    @AttributeOverride(name = "value", column = Column(name = "id"))
    val id: VideoGameStoreUserId,

    @Embedded
    val email: Email,

    @Embedded
    val password: Password,

    @Embedded
    val fullName: FullName,

    @Embedded
    val shippingAddress: ShippingAddress,

    @Embedded
    val age: Age,

    @Embedded
    val gender: Gender,

    @Enumerated(EnumType.STRING)
    val role: Role
 ) : LabeledEntity {
    override fun getId(): Identifier<out Any> = id
    override fun getLabel(): FullName = fullName
}



