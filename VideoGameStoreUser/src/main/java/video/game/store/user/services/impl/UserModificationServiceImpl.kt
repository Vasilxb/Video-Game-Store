package video.game.store.user.services.impl

import org.axonframework.commandhandling.gateway.CommandGateway
import org.springframework.stereotype.Service
import video.game.store.user.model.RegisterUserCommand
import video.game.store.user.model.UpdateUserAccountCommand
import video.game.store.user.model.AssignRoleCommand
import video.game.store.user.model.DeleteOrderCommand
import video.game.store.user.model.DeleteUserAccountCommand
import video.game.store.user.model.LoginUser2MFACommand
import video.game.store.user.model.LoginUserCommand
import video.game.store.user.model.LogoutUserCommand
import video.game.store.user.model.RegisterUser2MFACommand
import video.game.store.user.model.common.VideoGameStoreUserId
import video.game.store.user.services.UserModificationService
import java.util.concurrent.CompletableFuture

@Service
class UserModificationServiceImpl(
    val commandGateway: CommandGateway
) : UserModificationService {

    override fun registerUser(command: RegisterUserCommand): CompletableFuture<VideoGameStoreUserId> {
        return commandGateway.send(command)
    }

    override fun updateUserAccount(command: UpdateUserAccountCommand): CompletableFuture<Void> {
        return commandGateway.send(command)
    }

    override fun assignRole(command: AssignRoleCommand): CompletableFuture<Void> {
        return commandGateway.send(command)
    }
    override fun loginUser(command: LoginUserCommand): CompletableFuture<Any> =
        commandGateway.send(command)

    override fun registerUser2MFA(command: RegisterUser2MFACommand): CompletableFuture<Any> =
        commandGateway.send(command)

    override fun loginUser2MFA(command: LoginUser2MFACommand): CompletableFuture<Any> =
        commandGateway.send(command)

    override fun logoutUser(command: LogoutUserCommand): CompletableFuture<Void> =
        commandGateway.send(command)

    override fun deleteUserAccount(command: DeleteUserAccountCommand): CompletableFuture<Void> =
        commandGateway.send(command)

    override fun deleteOrder(command: DeleteOrderCommand): CompletableFuture<Void> =
        commandGateway.send(command)
}

