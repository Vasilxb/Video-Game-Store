package video.game.store.user.controllers

import org.axonframework.commandhandling.gateway.CommandGateway
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import video.game.store.user.model.DeleteUserAccountCommand
import video.game.store.user.model.LoginUser2MFACommand
import video.game.store.user.model.LoginUser2MFACommandDto
import video.game.store.user.model.LoginUserCommand
import video.game.store.user.model.LoginUserCommandDto
import video.game.store.user.model.LogoutUserCommand
import video.game.store.user.model.LogoutUserCommandDto
import video.game.store.user.model.RegisterUser2MFACommand
import video.game.store.user.model.RegisterUser2MFACommandDto
import video.game.store.user.model.RegisterUserCommand
import video.game.store.user.model.RegisterUserCommandDto
import video.game.store.user.model.common.VideoGameStoreUserId
import video.game.store.user.model.views.VideoGameStoreUserView
import video.game.store.user.services.UserViewReadService

@RestController
@RequestMapping("/api/user")
class UserRestApi(
    val userViewReadService: UserViewReadService,
    val commandGateway: CommandGateway
) {

    @PostMapping("/register")
    fun register(@RequestBody dto: RegisterUserCommandDto): ResponseEntity<Any> =
        ResponseEntity.ok(
            commandGateway.send<Any>(
                RegisterUserCommand(
                    id = VideoGameStoreUserId(),
                    fullname = dto.fullname,
                    email = dto.email,
                    password = dto.password,
                    shippingAddress = dto.shippingAddress,
                    age = dto.age,
                    gender = dto.gender
                )
            )
        )

    @PostMapping("/register-2MFA")
    fun register2MFA(@RequestBody dto: RegisterUser2MFACommandDto): ResponseEntity<Any> =
        ResponseEntity.ok(commandGateway.send<Any>(RegisterUser2MFACommand(dto.videoGameStoreUserId)))

    @PostMapping("/login")
    fun login(@RequestBody dto: LoginUserCommandDto): ResponseEntity<Any> =
        ResponseEntity.ok(
            commandGateway.send<Any>(
                LoginUserCommand(
                    id = dto.videoGameStoreUserId,
                    email = dto.email,
                    password = dto.password
                )
            )
        )

    @PostMapping("/login-2MFA")
    fun login2MFA(@RequestBody dto: LoginUser2MFACommandDto): ResponseEntity<Any> =
        ResponseEntity.ok(commandGateway.send<Any>(LoginUser2MFACommand(dto.videoGameStoreUserId)))

    @PostMapping("/logout")
    fun logout(@RequestBody dto: LogoutUserCommandDto): ResponseEntity<Any> =
        ResponseEntity.ok(commandGateway.send<Any>(LogoutUserCommand(dto.videoGameStoreUserId)))

    @DeleteMapping("/{userId}/deleteAccount")
    fun deleteAccount(@PathVariable userId: String): ResponseEntity<Any> =
        ResponseEntity.ok(commandGateway.send<Any>(DeleteUserAccountCommand(VideoGameStoreUserId(userId))))

    @GetMapping("/user-info")
    fun userInfo(@RequestParam userId: String): VideoGameStoreUserView =
        userViewReadService.findById(VideoGameStoreUserId(userId))

}

