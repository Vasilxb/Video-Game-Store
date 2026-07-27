package com.vgs.ordermanagement.services.impl

import com.vgs.ordermanagement.model.CreateOrderCommand
import com.vgs.ordermanagement.model.UpdateStatusCommand
import com.vgs.ordermanagement.model.common.OrderId
import com.vgs.ordermanagement.services.OrderModificationService
import org.axonframework.commandhandling.gateway.CommandGateway
import org.springframework.stereotype.Service
import java.util.concurrent.CompletableFuture

@Service
class OrderModificationServiceImpl (
    val commandGateway: CommandGateway,
) : OrderModificationService {
    override fun createOrder(command: CreateOrderCommand): CompletableFuture<OrderId> {
        // TODO: check here if the videoGameId exists in the table and has enough capacity
        return commandGateway.send(command)
    }

    override fun updateStatus(command: UpdateStatusCommand): CompletableFuture<OrderId> {
        return commandGateway.send(command)
    }
}