package com.vgs.catalog.config

import com.vgs.catalog.model.VideoGame
import com.vgs.catalog.model.common.VideoGameId
import org.axonframework.common.jpa.SimpleEntityManagerProvider
import org.axonframework.eventhandling.EventBus
import org.axonframework.messaging.annotation.ParameterResolverFactory
import org.axonframework.modelling.command.GenericJpaRepository
import org.axonframework.modelling.command.Repository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext

@Configuration
open class VideoGameRepositoryConfiguration(
    @PersistenceContext val entityManager: EntityManager
) {

    @Bean("axonVideoGameRepository")
    fun videoGameGenericJpaRepository(
        eventBus: EventBus,
        parameterResolverFactory: ParameterResolverFactory
    ): Repository<VideoGame> {

        return GenericJpaRepository.builder(VideoGame::class.java)
            .entityManagerProvider(
                SimpleEntityManagerProvider(entityManager)
            )
            .parameterResolverFactory(parameterResolverFactory)
            .eventBus(eventBus)
            .identifierConverter { VideoGameId(it) }
            .build()
    }
}