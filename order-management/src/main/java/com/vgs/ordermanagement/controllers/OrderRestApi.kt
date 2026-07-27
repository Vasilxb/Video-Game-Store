package com.vgs.ordermanagement.controllers

import com.vgs.ordermanagement.model.common.OrderId
import com.vgs.ordermanagement.model.common.UserId
import com.vgs.ordermanagement.model.common.VideoGameId
import com.vgs.ordermanagement.model.views.OrderView
import com.vgs.ordermanagement.services.OrderViewReadService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/orders")
class OrderRestApi (
    val orderViewReadService: OrderViewReadService
) {
    @GetMapping("/by-authenticated-user/all")
    fun findAll(): List<OrderView> {
        return orderViewReadService.findAllByUserId(UserId()) // TODO: get the authenticated user
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: OrderId): OrderView {
        return orderViewReadService.findById(id)
    }

    @GetMapping("/by-video-game-id/{id}")
    fun findByVideoGameId(@PathVariable id: VideoGameId): List<OrderView> {
        return orderViewReadService.findAllByVideoGameId(id)
    }
}