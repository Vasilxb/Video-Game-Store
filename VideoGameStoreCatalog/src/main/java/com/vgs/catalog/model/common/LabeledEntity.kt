package com.vgs.catalog.model.common

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.ZonedDateTime

interface LabeledEntity {

    @JsonProperty("id")
    fun getId(): Identifier

    @JsonProperty("label")
    fun getLabel(): String

    @JsonProperty("entityType")
    fun getEntityType(): String = this.javaClass.simpleName

    @JsonProperty("dateCreated")
    fun dateCreated(): ZonedDateTime? = null

    @JsonProperty("archived")
    fun isArchived(): Boolean = false
}