package com.vgs.ordermanagement.controllers

import com.vgs.ordermanagement.model.CreateOrderCommand
import com.vgs.ordermanagement.model.CreateOrderCommandDto
import com.vgs.ordermanagement.model.UpdateOrderStatusDto
import com.vgs.ordermanagement.model.UpdateStatusCommand
import com.vgs.ordermanagement.model.common.UserId
import com.vgs.ordermanagement.services.OrderModificationService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/submitCommand")
class OrderCommandDispatcher (
    val orderModificationService: OrderModificationService
) {
    @PostMapping("/CreateOrderCommand")
    fun createOrder(@RequestBody commandDto: CreateOrderCommandDto) : ResponseEntity<Any> {
        return ResponseEntity.ok(
            orderModificationService
                .createOrder(
                    CreateOrderCommand(
                        amount = commandDto.amount,
                        status = commandDto.status,
                        videoGameId = commandDto.videoGameId,
                        userId = UserId() // TODO: get the authenticated user
                    )
                ).get()
        )
    }

    @PostMapping("/UpdateOrderStatusCommand")
    fun updateOrderStatus(@RequestBody commandDto: UpdateOrderStatusDto) : ResponseEntity<Any> {
        return ResponseEntity.ok(
            orderModificationService
                .updateStatus(
                    UpdateStatusCommand(
                        status = commandDto.status,
                        orderId = commandDto.orderId,
                    )
                ).get()
        )
    }
}