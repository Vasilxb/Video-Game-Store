package com.vgs.ordermanagement.model.exceptions

import com.vgs.ordermanagement.model.common.VideoGameId

class VideoGameNotFoundException(val id: VideoGameId)
    : RuntimeException("Video Game with id $id not found") {
}
