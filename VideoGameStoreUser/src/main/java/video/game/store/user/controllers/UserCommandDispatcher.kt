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
import video.game.store.user.model.RegisterUser2MFACommand
import video.game.store.user.model.LoginUser2MFACommand
import video.game.store.user.model.LogoutUserCommand
import video.game.store.user.model.DeleteUserAccountCommand
import video.game.store.user.model.DeleteOrderCommand
import video.game.store.user.model.DeleteOrderCommandDto
import video.game.store.user.model.LoginUser2MFACommandDto
import video.game.store.user.model.LoginUserCommandDto
import video.game.store.user.model.LogoutUserCommandDto
import video.game.store.user.model.RegisterUser2MFACommandDto
import video.game.store.user.model.RegisterUserCommandDto
import video.game.store.user.model.UpdateUserAccountCommandDto
import video.game.store.user.model.common.VideoGameStoreUserId
import video.game.store.user.model.common.VideoGameOrderId
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

    @PostMapping("/RegisterUser2MFACommand")
    fun register2MFA(@RequestBody dto: RegisterUser2MFACommandDto): ResponseEntity<Any> =
        ResponseEntity.ok(
            userModificationService.registerUser2MFA(
                RegisterUser2MFACommand(
                    id = dto.videoGameStoreUserId
                )
            )
        )

    @PostMapping("/LoginUser2MFACommand")
    fun login2MFA(@RequestBody dto: LoginUser2MFACommandDto): ResponseEntity<Any> =
        ResponseEntity.ok(
            userModificationService.loginUser2MFA(
                LoginUser2MFACommand(
                    id = dto.videoGameStoreUserId
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
    fun deleteUserAccount(@RequestBody dto: LogoutUserCommandDto): ResponseEntity<Any> =
        ResponseEntity.ok(
            userModificationService.deleteUserAccount(
                DeleteUserAccountCommand(dto.videoGameStoreUserId)
            )
        )

    @PostMapping("/DeleteOrderCommand")
    fun deleteOrder(@RequestBody dto: DeleteOrderCommandDto): ResponseEntity<Any> =
        ResponseEntity.ok(
            userModificationService.deleteOrder(
                DeleteOrderCommand(
                    id = dto.videoGameStoreUserId,
                    videoGameOrderId = VideoGameOrderId(dto.videoGameOrderId.value)
                )
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

