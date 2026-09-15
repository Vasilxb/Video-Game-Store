package com.vgs.catalog.controllers

import com.vgs.catalog.model.CreateVideoGameCommand
import com.vgs.catalog.model.CreateVideoGameCommandDto
import com.vgs.catalog.model.DeleteVideoGameCommand
import com.vgs.catalog.model.DeleteVideoGameCommandDto
import com.vgs.catalog.model.UpdateVideoGameCommand
import com.vgs.catalog.model.UpdateVideoGameCommandDto
import com.vgs.catalog.services.VideoGameModificationService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/submitCommand")
class CatalogCommandDispatcher(
    val videoGameModificationService: VideoGameModificationService
) {

    @PostMapping("/CreateVideoGameCommand")
    fun createVideoGame(
        @RequestBody commandDto: CreateVideoGameCommandDto
    ): ResponseEntity<Any> {

        return ResponseEntity.ok(
            videoGameModificationService
                .createVideoGame(
                    CreateVideoGameCommand(
                        name = commandDto.name,
                        price = commandDto.price,
                        platform = commandDto.platform,
                        year = commandDto.year,
                        studio = commandDto.studio,
                        rating = commandDto.rating,
                        storeId = commandDto.storeId,
                        capacity = commandDto.capacity
                    )
                )
                .get()
        )
    }


    @PostMapping("/UpdateVideoGameCommand")
    fun updateVideoGame(
        @RequestBody commandDto: UpdateVideoGameCommandDto
    ): ResponseEntity<Any> {

        return ResponseEntity.ok(
            videoGameModificationService
                .updateVideoGame(
                    UpdateVideoGameCommand(
                        id = commandDto.id,
                        name = commandDto.name,
                        price = commandDto.price,
                        platform = commandDto.platform,
                        year = commandDto.year,
                        studio = commandDto.studio,
                        rating = commandDto.rating,
                        storeId = commandDto.storeId,
                        capacity = commandDto.capacity
                    )
                )
                .get()
        )
    }


    @PostMapping("/DeleteVideoGameCommand")
    fun deleteVideoGame(
        @RequestBody commandDto: DeleteVideoGameCommandDto
    ): ResponseEntity<Any> {

        return ResponseEntity.ok(
            videoGameModificationService
                .deleteVideoGame(
                    DeleteVideoGameCommand(
                        id = commandDto.id
                    )
                )
                .get()
        )
    }
}