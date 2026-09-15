package com.vgs.ordermanagement.controllers

import com.vgs.ordermanagement.model.CreateOrderCommand
import com.vgs.ordermanagement.model.CreateOrderCommandDto
import com.vgs.ordermanagement.model.DeleteOrderCommand
import com.vgs.ordermanagement.model.DeleteOrderCommandDto
import com.vgs.ordermanagement.model.UpdateOrderStatusCommandDto
import com.vgs.ordermanagement.model.UpdateStatusCommand
import com.vgs.ordermanagement.model.common.UserId
import com.vgs.ordermanagement.services.OrderModificationService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/submitCommand")
class OrderCommandDispatcher(
    val orderModificationService: OrderModificationService
) {

    @PostMapping("/CreateOrderCommand")
    fun createOrder(
        @RequestBody commandDto: CreateOrderCommandDto,
        @AuthenticationPrincipal jwt: Jwt
    ): ResponseEntity<Any> {

        val authenticatedUserId = authenticatedUserId(jwt)

        return ResponseEntity.ok(
            orderModificationService
                .createOrder(
                    CreateOrderCommand(
                        videoGameId = commandDto.videoGameId,
                        userId = authenticatedUserId
                    )
                )
                .get()
        )
    }

    @PostMapping("/UpdateOrderStatusCommand")
    fun updateOrderStatus(
        @RequestBody commandDto: UpdateOrderStatusCommandDto
    ): ResponseEntity<Any> {
        return ResponseEntity.ok(
            orderModificationService
                .updateStatus(
                    UpdateStatusCommand(
                        status = commandDto.status,
                        id = commandDto.orderId
                    )
                )
                .get()
        )
    }

    @PostMapping("/DeleteOrderCommand")
    fun deleteOrder(
        @RequestBody commandDto: DeleteOrderCommandDto
    ): ResponseEntity<Any> {
        return ResponseEntity.ok(
            orderModificationService
                .deleteOrder(
                    DeleteOrderCommand(
                        id = commandDto.orderId
                    )
                )
                .get()
        )
    }

    private fun authenticatedUserId(jwt: Jwt): UserId {
        val subject = jwt.subject

        if (subject.isNullOrBlank()) {
            throw ResponseStatusException(
                HttpStatus.UNAUTHORIZED,
                "JWT does not contain a subject."
            )
        }

        val userIdValue =
            if (subject.startsWith("VideoGameStoreUser:")) {
                subject
            } else {
                "VideoGameStoreUser:$subject"
            }

        return UserId(userIdValue)
    }
}