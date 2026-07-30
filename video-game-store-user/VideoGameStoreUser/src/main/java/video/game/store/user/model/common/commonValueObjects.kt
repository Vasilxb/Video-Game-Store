package video.game.store.user.model.common

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import jakarta.persistence.MappedSuperclass
import video.game.store.user.model.common.ShippingAddress
import java.io.Serializable
import java.math.BigDecimal
import java.time.ZonedDateTime
import kotlin.text.isNotEmpty
import kotlin.text.matches

@MappedSuperclass
abstract class Identifier<T>(providedValue: String, @Transient val entityClass: Class<T>) : Serializable {

    val value = "${entityClass.simpleName}:${providedValue.replace(".*:".toRegex(), "")}"

    override fun hashCode(): Int {
        return this.entityClass.hashCode() + this.value.hashCode()
    }

    fun baseValue() = value.split(":")[1]

    fun prefixedValue() = value

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Identifier<*>

        if (entityClass != other.entityClass) return false
        if (value != other.value) return false

        return true
    }

    override fun toString(): String = value
}

interface LabeledEntity {

    @JsonProperty("id")
    fun getId(): Identifier<out Any>

    @JsonProperty("label")
    fun getLabel(): FullName

    @JsonProperty("entityType")
    fun getEntityType(): String = this.javaClass.simpleName

    @JsonProperty("dateCreated")
    fun dateCreated(): ZonedDateTime? = null

    @JsonProperty("archived")
    fun isArchived(): Boolean = false
}


@Embeddable
data class Email(val value: String) {

    protected constructor() : this("")

    init {
        require(value.isNotEmpty()) { "Email cannot be empty." }
        require(value.matches("^[\\w.%+-]+@[\\w.-]+\\.[A-Za-z]{2,}$".toRegex())) {
            "Invalid email address $value"
        }
    }

    override fun toString(): String {
        return value
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Email

        return value == other.value
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }
}

@Embeddable
data class FullName(val value: String) {
    protected constructor() : this("")
    init {
        require(value.isNotEmpty()) { "Full name cannot be empty." }
        require(value.matches("^[A-Za-z]+([ '-][A-Za-z]+)*\\s+[A-Za-z]+([ '-][A-Za-z]+)*$".toRegex())) {
            "Invalid full name: $value"
        }
    }
    override fun toString(): String {
        return value
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as FullName

        return value == other.value
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }
}

@Embeddable
data class ShippingAddress(val value: String) {
    protected constructor() : this("")
    init {
        require(value.isNotEmpty()) { "Shipping address cannot be empty." }
        require(
            value.matches(
                "^[A-Za-z]+(?:\\s+[A-Za-z]+)*\\s+br\\.?\\s*\\d+\\s+\\d{3,10}$"
                    .toRegex()
            )
        ) { "Invalid shipping address: $value" }
    }

    override fun toString(): String {
        return value
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ShippingAddress

        return value == other.value
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }
}

@Embeddable
data class Age(val value: Int) {
    protected constructor() : this(0)

    init {
        require(value in 18..100) { "Age must be between 18 and 100." }
    }

    override fun toString(): String {
        return value.toString()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Age

        return value == other.value
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }
}

@Embeddable
data class Gender(val value: String) {
    protected constructor() : this("")

    init {
        val g = value.trim().lowercase()

        require(g.isNotEmpty()) { "Please pick a gender." }

        require(
            g in setOf(
                "male",
                "female",
                "non-binary",
                "nonbinary",
                "other",
                "prefer not to say"
            )
        ) { "Invalid gender: $value" }
    }
    override fun toString(): String {
        return value
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Gender

        return value == other.value
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }
}

