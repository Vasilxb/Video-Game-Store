package com.vgs.catalog.model.events

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import com.vgs.catalog.model.common.Identifier

abstract class AbstractEvent(
    open val identifier: Identifier
) {

    @JsonProperty("_eventType")
    fun eventType(): String = this.javaClass.simpleName

    @get:JsonIgnore
    protected abstract val aggregateClass: Class<*>

    @JsonIgnore
    fun eventTopic(): String {
        val aggregateName = aggregateClass.simpleName

        val aggregate = aggregateName
            .replace(
                Regex("([A-Z]+)([A-Z][a-z])"),
                "$1-$2"
            )
            .replace(
                Regex("([a-z])([A-Z])"),
                "$1-$2"
            )
            .lowercase()

        val action = this.javaClass.simpleName
            .removeSuffix("Event")
            .removePrefix(aggregateName)
            .replace(
                Regex("([A-Z]+)([A-Z][a-z])"),
                "$1.$2"
            )
            .replace(
                Regex("([a-z])([A-Z])"),
                "$1.$2"
            )
            .lowercase()

        return "$aggregate.$action"
    }

    @JsonIgnore
    open fun toExternalEvent(): Any? = null
}