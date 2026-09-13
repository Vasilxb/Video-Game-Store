package com.vgs.catalog.model

import com.vgs.catalog.model.common.Identifier
import com.vgs.catalog.model.common.LabeledEntity
import com.vgs.catalog.model.common.Money
import com.vgs.catalog.model.common.UserId
import com.vgs.catalog.model.common.VideoGameId
import com.vgs.catalog.model.enums.Platform
import com.vgs.catalog.model.events.VideoGameCapacityChangedEvent
import com.vgs.catalog.model.events.VideoGameCreatedEvent
import com.vgs.catalog.model.events.VideoGameDeletedEvent
import com.vgs.catalog.model.events.VideoGameUpdatedEvent
import jakarta.persistence.AttributeOverride
import jakarta.persistence.Column
import jakarta.persistence.Embedded
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Table
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.modelling.command.AggregateLifecycle
import org.axonframework.spring.stereotype.Aggregate
import java.time.ZonedDateTime

@Aggregate(repository = "axonVideoGameRepository")
@Entity
@Table(name = "video_game_aggregates")
class VideoGame : LabeledEntity {

    @AggregateIdentifier
    @EmbeddedId
    @AttributeOverride(
        name = "value",
        column = Column(name = "id")
    )
    private lateinit var id: VideoGameId

    private lateinit var updatedAt: ZonedDateTime

    private lateinit var name: String

    private lateinit var price: Money

    @Enumerated(EnumType.STRING)
    private lateinit var platform: Platform

    @Column(name = "release_year")
    private var year: Int = 0

    private lateinit var studio: String

    private var rating: Double = 0.0

    @Embedded
    @AttributeOverride(
        name = "value",
        column = Column(name = "store_id")
    )
    private lateinit var storeId: UserId

    private var capacity: Int = 0


    @CommandHandler
    constructor(command: CreateVideoGameCommand) {

        val event = VideoGameCreatedEvent(
            id = command.id,
            updatedAt = command.updatedAt,
            name = command.name,
            price = command.price,
            platform = command.platform,
            year = command.year,
            studio = command.studio,
            rating = command.rating,
            storeId = command.storeId,
            capacity = command.capacity
        )

        this.on(event)
        AggregateLifecycle.apply(event)
    }


    @CommandHandler
    fun update(command: UpdateVideoGameCommand): VideoGameId {

        val event = VideoGameUpdatedEvent(
            id = command.id,
            updatedAt = command.updatedAt,
            name = command.name,
            price = command.price,
            platform = command.platform,
            year = command.year,
            studio = command.studio,
            rating = command.rating,
            storeId = command.storeId,
            capacity = command.capacity
        )

        this.on(event)
        AggregateLifecycle.apply(event)

        return this.id
    }


    @CommandHandler
    fun delete(command: DeleteVideoGameCommand): VideoGameId {

        val event = VideoGameDeletedEvent(
            id = command.id,
            updatedAt = command.updatedAt
        )

        this.on(event)
        AggregateLifecycle.apply(event)

        return command.id
    }


    fun on(event: VideoGameCreatedEvent) {
        this.id = event.id
        this.updatedAt = event.updatedAt
        this.name = event.name
        this.price = event.price
        this.platform = event.platform
        this.year = event.year
        this.studio = event.studio
        this.rating = event.rating
        this.storeId = event.storeId
        this.capacity = event.capacity
    }


    fun on(event: VideoGameUpdatedEvent) {
        this.updatedAt = event.updatedAt
        this.name = event.name
        this.price = event.price
        this.platform = event.platform
        this.year = event.year
        this.studio = event.studio
        this.rating = event.rating
        this.storeId = event.storeId
        this.capacity = event.capacity
    }


    fun on(event: VideoGameCapacityChangedEvent) {
        this.updatedAt = event.updatedAt
        this.capacity = event.capacity
    }


    fun on(event: VideoGameDeletedEvent) {
        AggregateLifecycle.markDeleted()
    }


    override fun getId(): Identifier {
        return this.id
    }

    override fun getLabel(): String {
        return "Video Game: $id"
    }

    @CommandHandler
    fun decreaseCapacity(
        command: DecreaseVideoGameCapacityCommand
    ) {
        require(command.amount > 0) {
            "Amount must be greater than zero"
        }

        require(capacity >= command.amount) {
            "Video game capacity cannot be negative"
        }

        val event = VideoGameCapacityChangedEvent(
            id = this.id,
            updatedAt = ZonedDateTime.now(),

            // New total used internally by Catalog
            capacity = capacity - command.amount,

            // Amount sent to other microservices
            capacityChange = -command.amount
        )

        this.on(event)
        AggregateLifecycle.apply(event)
    }


    @CommandHandler
    fun increaseCapacity(
        command: IncreaseVideoGameCapacityCommand
    ) {
        require(command.amount > 0) {
            "Amount must be greater than zero"
        }

        val event = VideoGameCapacityChangedEvent(
            id = this.id,
            updatedAt = ZonedDateTime.now(),

            // New total used internally by Catalog
            capacity = capacity + command.amount,

            // Amount sent to other microservices
            capacityChange = command.amount
        )

        this.on(event)
        AggregateLifecycle.apply(event)
    }
}