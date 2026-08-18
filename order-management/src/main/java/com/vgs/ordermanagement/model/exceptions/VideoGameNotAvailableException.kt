package com.vgs.ordermanagement.model.exceptions

import com.vgs.ordermanagement.model.common.VideoGameId

class VideoGameNotAvailableException(val id: VideoGameId)
    : RuntimeException("Video Game with id $id has capacity 0.") {
}
