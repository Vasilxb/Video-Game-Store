package com.vgs.ordermanagement.handlers

import com.vgs.ordermanagement.model.common.VideoGameId
import org.axonframework.commandhandling.gateway.CommandGateway
import org.axonframework.eventhandling.EventHandler
import org.springframework.stereotype.Component

@Component
class AcademicActivityRecordExternalEventHandler(
    val commandGateway: CommandGateway,
) {
//    TODO: when a video game added event occurs, add the entry to a catalog table
//    TODO: do the same when video game removed event happens
//    @EventHandler
//    fun handle(event: CatalogVideoGameAddedEvent) {
//        commandGateway.sendAndWait<VideoGameId>(
//            AddVideoGameToInventoryCommand(
//                professorProfileId = event.id
//            )
//        )
//    }
}
