package com.vgs.catalog.services.impl

import com.vgs.catalog.model.CreateVideoGameCommand
import com.vgs.catalog.model.DeleteVideoGameCommand
import com.vgs.catalog.model.UpdateVideoGameCommand
import com.vgs.catalog.model.common.VideoGameId
import com.vgs.catalog.services.VideoGameModificationService
import org.axonframework.commandhandling.gateway.CommandGateway
import org.springframework.stereotype.Service
import java.util.concurrent.CompletableFuture

@Service
class VideoGameModificationServiceImpl(
    val commandGateway: CommandGateway
) : VideoGameModificationService {

    override fun createVideoGame(
        command: CreateVideoGameCommand
    ): CompletableFuture<VideoGameId> {
        return commandGateway.send(command)
    }

    override fun updateVideoGame(
        command: UpdateVideoGameCommand
    ): CompletableFuture<VideoGameId> {
        return commandGateway.send(command)
    }

    override fun deleteVideoGame(
        command: DeleteVideoGameCommand
    ): CompletableFuture<VideoGameId> {
        return commandGateway.send(command)
    }
}