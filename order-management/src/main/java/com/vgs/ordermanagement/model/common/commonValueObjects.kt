package com.vgs.ordermanagement.model.common

import jakarta.persistence.Embeddable
import java.util.*
import com.fasterxml.jackson.annotation.JsonProperty
import com.vgs.ordermanagement.model.Order
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.MappedSuperclass
import jakarta.persistence.Table
import java.time.ZonedDateTime
import java.io.Serializable
import java.math.BigDecimal


@MappedSuperclass
abstract class Identifier<T>(providedValue: String, @Transient val entityClass: Class<T>) : Serializable {

    open val value = "${entityClass.simpleName}:${providedValue.replace(".*:".toRegex(), "")}"

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
    fun getLabel(): String

    @JsonProperty("entityType")
    fun getEntityType(): String = this.javaClass.simpleName

    @JsonProperty("dateCreated")
    fun dateCreated(): ZonedDateTime? = null

    @JsonProperty("archived")
    fun isArchived(): Boolean = false
}


@Embeddable
data class Money(@Column(name = "amount") val amount: BigDecimal, val currency: String) : Serializable {

    init {
        require(amount >= BigDecimal(0)) { "Amount must be greater than or equal to zero" }
        require(currency.matches("[A-Z]{3}".toRegex())) { "Currency code must be a three-letter uppercase string" }
    }

    constructor(doubleAmount: Double, currency: String) : this(BigDecimal(doubleAmount), currency)

    constructor(stringAmount: String, currency: String) : this(
        try {
            BigDecimal(stringAmount)
        } catch (e: NumberFormatException) {
            BigDecimal(0)
        }, currency
    )


    override fun toString(): String {
        return amount.toString()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Money

        if (amount != other.amount) return false

        return true
    }

    override fun hashCode(): Int {
        return amount.hashCode()
    }

}

