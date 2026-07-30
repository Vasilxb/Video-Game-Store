package video.game.store.user.model

import jakarta.persistence.AttributeOverride
import jakarta.persistence.Column
import jakarta.persistence.EmbeddedId
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.spring.stereotype.Aggregate
import video.game.store.user.model.common.Age
import video.game.store.user.model.common.Email
import video.game.store.user.model.common.FullName
import video.game.store.user.model.common.Gender
import video.game.store.user.model.common.Identifier
import video.game.store.user.model.common.LabeledEntity
import video.game.store.user.model.common.ShippingAddress
import video.game.store.user.model.common.VideoGameStoreUserId

@Aggregate
class VideoGameStoreUser : LabeledEntity {
    @AggregateIdentifier
    @EmbeddedId
    @AttributeOverride(name = "value", column = Column(name = "id"))
    private lateinit var id: VideoGameStoreUserId
    private lateinit var email: Email
    private lateinit var fullName: FullName
    private lateinit var shippingAddress: ShippingAddress
    private lateinit var age: Age
    private lateinit var gender: Gender


    override fun getId(): Identifier<out Any> {
        return this.id
    }

    override fun getLabel(): FullName {
        return this.fullName
    }
}