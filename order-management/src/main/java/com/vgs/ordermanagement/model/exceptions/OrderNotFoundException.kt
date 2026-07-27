package com.vgs.ordermanagement.model.exceptions

import com.vgs.ordermanagement.model.common.OrderId

class OrderNotFoundException(val id: OrderId)
    : RuntimeException("Order with id $id not found") {
}
