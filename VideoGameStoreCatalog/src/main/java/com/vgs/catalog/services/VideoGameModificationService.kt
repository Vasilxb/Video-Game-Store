package com.vgs.catalog.services

import com.vgs.catalog.model.CreateVideoGameCommand
import com.vgs.catalog.model.DeleteVideoGameCommand
import com.vgs.catalog.model.UpdateVideoGameCommand
import com.vgs.catalog.model.common.VideoGameId
import java.util.concurrent.CompletableFuture

interface VideoGameModificationService {

    fun createVideoGame(
        command: CreateVideoGameCommand
    ): CompletableFuture<VideoGameId>

    fun updateVideoGame(
        command: UpdateVideoGameCommand
    ): CompletableFuture<VideoGameId>

    fun deleteVideoGame(
        command: DeleteVideoGameCommand
    ): CompletableFuture<VideoGameId>
}