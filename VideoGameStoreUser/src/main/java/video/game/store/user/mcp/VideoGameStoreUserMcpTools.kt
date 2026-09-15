package video.game.store.user.mcp

import org.springframework.ai.tool.annotation.Tool
import org.springframework.stereotype.Component
import video.game.store.user.model.AssignRoleCommand
import video.game.store.user.model.AssignRoleCommandDto
import video.game.store.user.model.DeleteUserAccountCommand
import video.game.store.user.model.DeleteUserAccountCommandDto
import video.game.store.user.model.LoginUserCommand
import video.game.store.user.model.LoginUserCommandDto
import video.game.store.user.model.LogoutUserCommand
import video.game.store.user.model.LogoutUserCommandDto
import video.game.store.user.model.RegisterUserCommand
import video.game.store.user.model.RegisterUserCommandDto
import video.game.store.user.model.UpdateUserAccountCommand
import video.game.store.user.model.UpdateUserAccountCommandDto
import video.game.store.user.model.common.VideoGameStoreUserId
import video.game.store.user.model.views.VideoGameStoreUserView
import video.game.store.user.services.UserModificationService
import video.game.store.user.services.UserViewReadService

@Component
class VideoGameStoreUserMcpTools(
    private val userModificationService: UserModificationService,
    private val userViewReadService: UserViewReadService
) {

    @Tool(
        name = "register_user",
        description = "Register a new user in the video game store"
    )
    fun registerUser(request: RegisterUserCommandDto): VideoGameStoreUserId {
        return userModificationService.registerUser(
            RegisterUserCommand(
                id = VideoGameStoreUserId(),
                fullname = request.fullname,
                email = request.email,
                password = request.password,
                shippingAddress = request.shippingAddress,
                age = request.age,
                gender = request.gender
            )
        ).get()
    }

    @Tool(
        name = "find_user_by_id",
        description = "Find a video game store user by their ID"
    )
    fun findUserById(id: String): VideoGameStoreUserView {
        return userViewReadService.findById(
            VideoGameStoreUserId(id)
        )
    }

    @Tool(
        name = "find_all_users",
        description = "Find all registered video game store users"
    )
    fun findAllUsers(): List<VideoGameStoreUserView> {
        return userViewReadService.findAll()
    }

    @Tool(
        name = "find_user_by_email",
        description = "Find a video game store user by their email address"
    )
    fun findUserByEmail(email: String): VideoGameStoreUserView? {
        return userViewReadService.findByEmail(email)
    }

    @Tool(
        name = "update_user_account",
        description = "Update a video game store user's account information"
    )
    fun updateUserAccount(request: UpdateUserAccountCommandDto) {
        userModificationService.updateUserAccount(
            UpdateUserAccountCommand(
                id = request.videoGameStoreUserId,
                fullname = request.fullname,
                email = request.email,
                password = request.password,
                shippingAddress = request.shippingAddress,
                age = request.age,
                gender = request.gender
            )
        ).get()
    }

    @Tool(
        name = "assign_user_role",
        description = "Assign a role to a video game store user"
    )
    fun assignUserRole(request: AssignRoleCommandDto) {
        userModificationService.assignRole(
            AssignRoleCommand(
                id = request.videoGameStoreUserId,
                role = request.role
            )
        ).get()
    }

    @Tool(
        name = "login_user",
        description = "Log a video game store user into their account"
    )
    fun loginUser(request: LoginUserCommandDto): Any {
        return userModificationService.loginUser(
            LoginUserCommand(
                id = request.videoGameStoreUserId,
                email = request.email,
                password = request.password
            )
        ).get()
    }

    @Tool(
        name = "logout_user",
        description = "Log a video game store user out of their account"
    )
    fun logoutUser(request: LogoutUserCommandDto) {
        userModificationService.logoutUser(
            LogoutUserCommand(
                id = request.videoGameStoreUserId
            )
        ).get()
    }

    @Tool(
        name = "delete_user_account",
        description = "Delete a video game store user's account"
    )
    fun deleteUserAccount(request: DeleteUserAccountCommandDto) {
        userModificationService.deleteUserAccount(
            DeleteUserAccountCommand(
                id = request.videoGameStoreUserId
            )
        ).get()
    }
}
