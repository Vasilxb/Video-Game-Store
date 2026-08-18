package com.vgs.ordermanagement.services

import com.vgs.ordermanagement.model.CreateOrderCommand
import com.vgs.ordermanagement.model.DeleteOrderCommand
import com.vgs.ordermanagement.model.UpdateStatusCommand
import com.vgs.ordermanagement.model.common.OrderId
import java.util.concurrent.CompletableFuture

interface OrderModificationService {
    fun createOrder(command: CreateOrderCommand): CompletableFuture<OrderId>
    fun updateStatus(command: UpdateStatusCommand): CompletableFuture<OrderId>
}