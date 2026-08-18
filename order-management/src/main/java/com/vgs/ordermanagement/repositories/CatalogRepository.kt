package com.vgs.ordermanagement.repositories

import com.vgs.ordermanagement.model.common.VideoGameId
import com.vgs.ordermanagement.model.views.CatalogView
import org.springframework.data.jpa.repository.JpaRepository

interface CatalogRepository : JpaRepository<CatalogView, VideoGameId> {
}
