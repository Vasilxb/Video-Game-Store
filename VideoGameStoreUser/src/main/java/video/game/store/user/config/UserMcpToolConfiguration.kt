package video.game.store.user.config;

import video.game.store.user.mcp.VideoGameStoreUserMcpTools
import org.springframework.ai.tool.ToolCallbackProvider
import org.springframework.ai.tool.method.MethodToolCallbackProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class UserMcpToolConfiguration {

    @Bean
    fun videoGameStoreUserToolCallbackProvider(
        videoGameStoreUserMcpTools: VideoGameStoreUserMcpTools
    ):ToolCallbackProvider =
        MethodToolCallbackProvider.builder()
            .toolObjects(videoGameStoreUserMcpTools)
            .build()
}