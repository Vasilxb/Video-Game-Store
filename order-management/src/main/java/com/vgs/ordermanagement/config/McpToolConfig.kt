package com.vgs.ordermanagement.config

import com.vgs.ordermanagement.mcp.OrderMcpTools
import org.springframework.ai.tool.ToolCallbackProvider
import org.springframework.ai.tool.method.MethodToolCallbackProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class McpToolConfig {

    @Bean
    fun orderToolCallbackProvider(
        orderMcpTools: OrderMcpTools
    ): ToolCallbackProvider =
        MethodToolCallbackProvider.builder()
            .toolObjects(orderMcpTools)
            .build()
}
