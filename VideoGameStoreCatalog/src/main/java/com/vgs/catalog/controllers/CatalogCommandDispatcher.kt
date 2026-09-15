package com.vgs.catalog.controllers

import com.vgs.catalog.model.CreateVideoGameCommand
import com.vgs.catalog.model.CreateVideoGameCommandDto
import com.vgs.catalog.model.DeleteVideoGameCommand
import com.vgs.catalog.model.DeleteVideoGameCommandDto
import com.vgs.catalog.model.UpdateVideoGameCommand
import com.vgs.catalog.model.UpdateVideoGameCommandDto
import com.vgs.catalog.model.common.UserId
import com.vgs.catalog.services.VideoGameModificationService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/submitCommand")
class CatalogCommandDispatcher(
    val videoGameModificationService: VideoGameModificationService
) {

    @PostMapping("/CreateVideoGameCommand")
    fun createVideoGame(
        @RequestBody commandDto: CreateVideoGameCommandDto,
        @AuthenticationPrincipal jwt: Jwt
    ): ResponseEntity<Any> {

        val authenticatedStoreId = authenticatedUserId(jwt)

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
                        storeId = authenticatedStoreId,
                        capacity = commandDto.capacity
                    )
                )
                .get()
        )
    }


    @PostMapping("/UpdateVideoGameCommand")
    fun updateVideoGame(
        @RequestBody commandDto: UpdateVideoGameCommandDto,
        @AuthenticationPrincipal jwt: Jwt
    ): ResponseEntity<Any> {

        val authenticatedStoreId = authenticatedUserId(jwt)

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
                        storeId = authenticatedStoreId,
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


    private fun authenticatedUserId(jwt: Jwt): UserId {
        val subject = jwt.subject

        if (subject.isNullOrBlank()) {
            throw ResponseStatusException(
                HttpStatus.UNAUTHORIZED,
                "JWT does not contain a subject."
            )
        }

        val userIdValue =
            if (subject.startsWith("VideoGameStoreUser:")) {
                subject
            } else {
                "VideoGameStoreUser:$subject"
            }

        return UserId(userIdValue)
    }
}