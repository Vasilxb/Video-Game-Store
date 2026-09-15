package com.vgs.ordermanagement.mcp

import com.vgs.ordermanagement.model.CreateOrderCommand
import com.vgs.ordermanagement.model.CreateOrderCommandDto
import com.vgs.ordermanagement.model.UpdateOrderStatusCommandDto
import com.vgs.ordermanagement.model.UpdateStatusCommand
import com.vgs.ordermanagement.model.common.OrderId
import com.vgs.ordermanagement.model.common.UserId
import com.vgs.ordermanagement.model.common.VideoGameId
import com.vgs.ordermanagement.model.views.OrderView
import com.vgs.ordermanagement.services.OrderModificationService
import com.vgs.ordermanagement.services.OrderViewReadService
import jakarta.annotation.PostConstruct
import org.springframework.ai.tool.annotation.Tool
import org.springframework.stereotype.Component

@Component
class OrderMcpTools(
    private val orderModificationService: OrderModificationService,
    private val orderViewReadService: OrderViewReadService
) {
    @Tool(
        name = "create_order",
        description = "Create a new order for a video game purchase"
    )
    fun createOrder(request: CreateOrderCommandDto): OrderId {
        return orderModificationService.createOrder(
            CreateOrderCommand(
                videoGameId = request.videoGameId,
                userId = UserId()
            )
        ).get()
    }

    @Tool(
        name = "update_order_status",
        description = "Update the status of an existing order"
    )
    fun updateOrderStatus(request: UpdateOrderStatusCommandDto): OrderId {
        return orderModificationService.updateStatus(
            UpdateStatusCommand(id = request.orderId, status = request.status)
        ).get()
    }

    @Tool(
        name = "find_orders_by_user",
        description = "Find all orders for the currently authenticated user"
    )
    fun findAllOrdersForUser(): List<OrderView> {
        return orderViewReadService.findAllByUserId(UserId())
    }

    @Tool(
        name = "find_order_by_id",
        description = "Find an order by its ID"
    )
    fun findOrderById(id: String): OrderView {
        return orderViewReadService.findById(OrderId(id))
    }

    @Tool(
        name = "find_orders_by_video_game",
        description = "Find all orders for a given video game"
    )
    fun findOrdersByVideoGameId(videoGameId: String): List<OrderView> {
        return orderViewReadService.findAllByVideoGameId(VideoGameId(videoGameId))
    }
}