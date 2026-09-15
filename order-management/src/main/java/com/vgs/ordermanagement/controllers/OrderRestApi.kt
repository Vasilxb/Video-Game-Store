package com.vgs.ordermanagement.controllers

import com.vgs.ordermanagement.model.common.OrderId
import com.vgs.ordermanagement.model.common.UserId
import com.vgs.ordermanagement.model.common.VideoGameId
import com.vgs.ordermanagement.model.views.OrderView
import com.vgs.ordermanagement.services.OrderViewReadService
import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/api/orders")
class OrderRestApi(
    val orderViewReadService: OrderViewReadService
) {

    @GetMapping("/by-authenticated-user/all")
    fun findAllByAuthenticatedUser(
        @AuthenticationPrincipal jwt: Jwt
    ): List<OrderView> {
        return orderViewReadService.findAllByUserId(
            authenticatedUserId(jwt)
        )
    }

    @GetMapping("/{id}")
    fun findById(
        @PathVariable id: OrderId
    ): OrderView {
        return orderViewReadService.findById(id)
    }

    @GetMapping("/by-video-game-id/{id}")
    fun findByVideoGameId(
        @PathVariable id: VideoGameId
    ): List<OrderView> {
        return orderViewReadService.findAllByVideoGameId(id)
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