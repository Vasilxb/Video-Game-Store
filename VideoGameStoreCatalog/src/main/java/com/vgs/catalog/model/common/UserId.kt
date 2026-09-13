package com.vgs.catalog.model.common

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import java.io.Serializable

@Embeddable
data class UserId(
    @Column(name = "value")
    val value: String
) : Serializable {

    constructor() : this("")

    override fun toString(): String = value
}