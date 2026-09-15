package video.game.store.user.controllers

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import video.game.store.user.model.RegisterUserCommand
import video.game.store.user.model.UpdateUserAccountCommand
import video.game.store.user.model.AssignRoleCommand
import video.game.store.user.model.AssignRoleCommandDto
import video.game.store.user.model.LoginUserCommand
import video.game.store.user.model.LogoutUserCommand
import video.game.store.user.model.DeleteUserAccountCommand
import video.game.store.user.model.DeleteUserAccountCommandDto
import video.game.store.user.model.LoginUserCommandDto
import video.game.store.user.model.LogoutUserCommandDto
import video.game.store.user.model.RegisterUserCommandDto
import video.game.store.user.model.UpdateUserAccountCommandDto
import video.game.store.user.model.common.VideoGameStoreUserId
import video.game.store.user.services.UserModificationService

@RestController
@RequestMapping("/submitCommand")
class UserCommandDispatcher(
    private val userModificationService: UserModificationService
) {

    @PostMapping("/RegisterUserCommand")
    fun registerUser(@RequestBody commandDto: RegisterUserCommandDto): ResponseEntity<Any> =
        ResponseEntity.ok(
            userModificationService.registerUser(
                RegisterUserCommand(
                    id = VideoGameStoreUserId(),
                    fullname = commandDto.fullname,
                    email = commandDto.email,
                    password = commandDto.password,
                    shippingAddress = commandDto.shippingAddress,
                    age = commandDto.age,
                    gender = commandDto.gender
                )
            )
        )

    @PostMapping("/UpdateUserAccountCommand")
    fun updateUserAccount(@RequestBody commandDto: UpdateUserAccountCommandDto): ResponseEntity<Any> =
        ResponseEntity.ok(
            userModificationService.updateUserAccount(
                UpdateUserAccountCommand(
                    id = commandDto.videoGameStoreUserId,
                    fullname = commandDto.fullname,
                    email = commandDto.email,
                    password = commandDto.password,
                    shippingAddress = commandDto.shippingAddress,
                    age = commandDto.age,
                    gender = commandDto.gender
                )
            )
        )

    @PostMapping("/LoginUserCommand")
    fun loginUser(@RequestBody dto: LoginUserCommandDto): ResponseEntity<Any> =
        ResponseEntity.ok(
            userModificationService.loginUser(
                LoginUserCommand(
                    id = dto.videoGameStoreUserId,
                    email = dto.email,
                    password = dto.password
                )
            )
        )

    @PostMapping("/LogoutUserCommand")
    fun logoutUser(@RequestBody dto: LogoutUserCommandDto): ResponseEntity<Any> =
        ResponseEntity.ok(
            userModificationService.logoutUser(
                LogoutUserCommand(dto.videoGameStoreUserId)
            )
        )

    @PostMapping("/DeleteUserAccountCommand")
    fun deleteUserAccount(@RequestBody dto: DeleteUserAccountCommandDto): ResponseEntity<Any> =
        ResponseEntity.ok(
            userModificationService.deleteUserAccount(
                DeleteUserAccountCommand(dto.videoGameStoreUserId)
            )
        )

    @PostMapping("/AssignRoleCommand")
    fun assignRole(@RequestBody dto: AssignRoleCommandDto): ResponseEntity<Any> =
        ResponseEntity.ok(
            userModificationService.assignRole(
                AssignRoleCommand(
                    id = dto.videoGameStoreUserId,
                    role = dto.role
                )
            )
        )
}

