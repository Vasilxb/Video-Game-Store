package video.game.store.user.model.events


import video.game.store.user.model.DeleteOrderCommand
import video.game.store.user.model.common.VideoGameOrderId
import video.game.store.user.model.common.VideoGameStoreUserId


data class OrderDeletedExternalEvent(
    val id: VideoGameOrderId
)

data class OrderDeletedEvent(
    var videoGameStoreUserId: VideoGameStoreUserId,
    var videoGameOrderId: VideoGameOrderId
) {
    constructor(command: DeleteOrderCommand) : this(
        videoGameStoreUserId = command.id,
        videoGameOrderId = command.videoGameOrderId
    )
}



