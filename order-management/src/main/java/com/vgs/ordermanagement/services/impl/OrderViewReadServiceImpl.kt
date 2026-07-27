package com.vgs.ordermanagement.services.impl

import com.vgs.ordermanagement.model.common.OrderId
import com.vgs.ordermanagement.model.common.UserId
import com.vgs.ordermanagement.model.common.VideoGameId
import com.vgs.ordermanagement.model.exceptions.OrderNotFoundException
import com.vgs.ordermanagement.model.views.OrderView
import com.vgs.ordermanagement.repositories.OrderViewJpaRepository
import com.vgs.ordermanagement.services.OrderViewReadService
import org.springframework.stereotype.Service

@Service
class OrderViewReadServiceImpl (
    val orderViewJpaRepository: OrderViewJpaRepository
) : OrderViewReadService {
    override fun findById(id: OrderId): OrderView {
        return orderViewJpaRepository
            .findById(id)
            .orElseThrow { OrderNotFoundException(id) }
    }

    override fun findAll(): List<OrderView> {
        return orderViewJpaRepository.findAll()
    }

    override fun findAllByUserId(userId: UserId): List<OrderView> {
        return orderViewJpaRepository
            .findAllByUserId(userId);
    }

    override fun findAllByVideoGameId(videoGameId: VideoGameId): List<OrderView> {
        return orderViewJpaRepository
            .findAllByVideoGameId(videoGameId);
    }

    override fun findAllByUserIdAndVideoGameId(
        userId: UserId,
        videoGameId: VideoGameId
    ): List<OrderView> {
        return orderViewJpaRepository
            .findAllByUserIdAndVideoGameId(userId, videoGameId);
    }
}