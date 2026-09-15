package com.vgs.ordermanagement.controllers

import com.vgs.ordermanagement.model.CreateOrderCommand
import com.vgs.ordermanagement.model.CreateOrderCommandDto
import com.vgs.ordermanagement.model.DeleteOrderCommand
import com.vgs.ordermanagement.model.DeleteOrderCommandDto
import com.vgs.ordermanagement.model.UpdateOrderStatusCommandDto
import com.vgs.ordermanagement.model.UpdateStatusCommand
import com.vgs.ordermanagement.model.common.UserId
import com.vgs.ordermanagement.model.enums.OrderStatus
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
                        videoGameId = commandDto.videoGameId,
                        userId = UserId()
                    )
                ).get()
        )
    }

    @PostMapping("/UpdateOrderStatusCommand")
    fun updateOrderStatus(@RequestBody commandDto: UpdateOrderStatusCommandDto) : ResponseEntity<Any> {
        return ResponseEntity.ok(
            orderModificationService
                .updateStatus(
                    UpdateStatusCommand(
                        status = commandDto.status,
                        id = commandDto.orderId,
                    )
                ).get()
        )
    }

    @PostMapping("/DeleteOrderCommand")
    fun deleteOrder(@RequestBody commandDto: DeleteOrderCommandDto) : ResponseEntity<Any> {
        return ResponseEntity.ok(
            orderModificationService
                .deleteOrder(
                    DeleteOrderCommand(
                        id = commandDto.orderId,
                    )
                ).get()
        )
    }
}