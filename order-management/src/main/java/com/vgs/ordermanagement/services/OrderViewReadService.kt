package com.vgs.ordermanagement.services

import com.vgs.ordermanagement.model.common.OrderId
import com.vgs.ordermanagement.model.common.UserId
import com.vgs.ordermanagement.model.common.VideoGameId
import com.vgs.ordermanagement.model.views.OrderView

interface OrderViewReadService {
    fun findById(id: OrderId): OrderView
    fun findAll(): List<OrderView>
    fun findAllByUserId(userId: UserId): List<OrderView>
    fun findAllByVideoGameId(videoGameId: VideoGameId): List<OrderView>
    fun findAllByUserIdAndVideoGameId(userId: UserId, videoGameId: VideoGameId): List<OrderView>
}