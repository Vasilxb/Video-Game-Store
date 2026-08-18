package com.vgs.catalog.model.common

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import java.io.Serializable
import java.math.BigDecimal

@Embeddable
data class Money(
    @Column(name = "amount")
    val amount: BigDecimal,

    val currency: String
) : Serializable {

    init {
        require(amount >= BigDecimal.ZERO) {
            "Amount must be greater than or equal to zero"
        }

        require(currency.matches("[A-Z]{3}".toRegex())) {
            "Currency code must be a three-letter uppercase string"
        }
    }

    constructor(doubleAmount: Double, currency: String) :
            this(BigDecimal.valueOf(doubleAmount), currency)

    constructor(stringAmount: String, currency: String) : this(
        try {
            BigDecimal(stringAmount)
        } catch (e: NumberFormatException) {
            BigDecimal.ZERO
        },
        currency
    )

    override fun toString(): String = amount.toString()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Money

        return amount == other.amount
    }

    override fun hashCode(): Int = amount.hashCode()
}