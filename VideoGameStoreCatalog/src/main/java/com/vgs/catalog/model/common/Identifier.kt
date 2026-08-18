package com.vgs.catalog.model.common

import jakarta.persistence.MappedSuperclass
import jakarta.persistence.Transient
import java.io.Serializable

@MappedSuperclass
abstract class Identifier(
    providedValue: String,
    @Transient val entityClass: Class<*>
) : Serializable {

    open val value =
        "${entityClass.simpleName}:${providedValue.replace(".*:".toRegex(), "")}"

    override fun hashCode(): Int {
        return entityClass.hashCode() + value.hashCode()
    }

    fun baseValue(): String = value.split(":")[1]

    fun prefixedValue(): String = value

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Identifier

        return entityClass == other.entityClass &&
                value == other.value
    }

    override fun toString(): String = value
}