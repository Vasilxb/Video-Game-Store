package com.vgs.catalog.mcp

import org.springframework.ai.tool.ToolCallbackProvider
import org.springframework.ai.tool.method.MethodToolCallbackProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class CatalogMcpConfiguration {

    @Bean
    fun catalogMcpToolCallbackProvider(
        catalogMcpTools: CatalogMcpTools
    ): ToolCallbackProvider {
        return MethodToolCallbackProvider
            .builder()
            .toolObjects(catalogMcpTools)
            .build()
    }
}