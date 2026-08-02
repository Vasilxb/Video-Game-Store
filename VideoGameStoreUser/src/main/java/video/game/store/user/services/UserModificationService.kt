package video.game.store.user.services

import video.game.store.user.model.RegisterUserCommand
import video.game.store.user.model.UpdateUserAccountCommand
import video.game.store.user.model.AssignRoleCommand
import video.game.store.user.model.LoginUserCommand
import video.game.store.user.model.RegisterUser2MFACommand
import video.game.store.user.model.LoginUser2MFACommand
import video.game.store.user.model.LogoutUserCommand
import video.game.store.user.model.DeleteUserAccountCommand
import video.game.store.user.model.DeleteOrderCommand
import video.game.store.user.model.common.VideoGameStoreUserId
import java.util.concurrent.CompletableFuture

interface UserModificationService {
    fun registerUser(command: RegisterUserCommand): CompletableFuture<VideoGameStoreUserId>
    fun updateUserAccount(command: UpdateUserAccountCommand): CompletableFuture<Void>
    fun assignRole(command: AssignRoleCommand): CompletableFuture<Void>
    fun loginUser(command: LoginUserCommand): CompletableFuture<Any>
    fun registerUser2MFA(command: RegisterUser2MFACommand): CompletableFuture<Any>
    fun loginUser2MFA(command: LoginUser2MFACommand): CompletableFuture<Any>
    fun logoutUser(command: LogoutUserCommand): CompletableFuture<Void>
    fun deleteUserAccount(command: DeleteUserAccountCommand): CompletableFuture<Void>
    fun deleteOrder(command: DeleteOrderCommand): CompletableFuture<Void>
}

