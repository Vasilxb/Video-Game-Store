package com.vgs.ordermanagement.services.impl

import com.vgs.ordermanagement.model.CreateOrderCommand
import com.vgs.ordermanagement.model.UpdateStatusCommand
import com.vgs.ordermanagement.model.common.OrderId
import com.vgs.ordermanagement.model.exceptions.VideoGameNotAvailableException
import com.vgs.ordermanagement.repositories.CatalogRepository
import com.vgs.ordermanagement.services.OrderModificationService
import org.axonframework.commandhandling.gateway.CommandGateway
import org.springframework.stereotype.Service
import java.util.concurrent.CompletableFuture

@Service
class OrderModificationServiceImpl (
    val commandGateway: CommandGateway,
    val catalogRepository: CatalogRepository
) : OrderModificationService {
    override fun createOrder(command: CreateOrderCommand): CompletableFuture<OrderId> {
        val game = catalogRepository.findById(command.videoGameId)
            .orElseThrow { VideoGameNotAvailableException(command.videoGameId) }

        if (game.capacity == 0) throw VideoGameNotAvailableException(command.videoGameId)

        // make sure user doesn't give fake price in the api call
        command.amount = game.price

        return commandGateway.send(command)
    }

    override fun updateStatus(command: UpdateStatusCommand): CompletableFuture<OrderId> {
        return commandGateway.send(command)
    }
}