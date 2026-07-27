package com.vgs.ordermanagement.repositories

import com.vgs.ordermanagement.model.common.OrderId
import com.vgs.ordermanagement.model.common.UserId
import com.vgs.ordermanagement.model.common.VideoGameId
import com.vgs.ordermanagement.model.views.OrderView
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface OrderViewJpaRepository : JpaRepository<OrderView, OrderId>{
    fun findAllByUserId(userId: UserId): List<OrderView>
    fun findAllByVideoGameId(videoGameId: VideoGameId): List<OrderView>
    fun findAllByUserIdAndVideoGameId(userId: UserId, videoGameId: VideoGameId): List<OrderView>
}