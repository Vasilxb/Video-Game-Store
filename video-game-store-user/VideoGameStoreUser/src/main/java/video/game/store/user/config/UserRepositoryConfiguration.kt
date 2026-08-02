package video.game.store.user.config

import org.axonframework.common.jpa.SimpleEntityManagerProvider
import org.axonframework.eventhandling.EventBus
import org.axonframework.messaging.annotation.ParameterResolverFactory
import org.axonframework.modelling.command.GenericJpaRepository
import org.axonframework.modelling.command.Repository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import video.game.store.user.model.VideoGameStoreUser
import video.game.store.user.model.common.VideoGameStoreUserId
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext

@Configuration
open class UserRepositoryConfiguration(@PersistenceContext val entityManager: EntityManager) {

    @Bean("axonUserRepository")
    fun userGenericJpaRepository(
        eventBus: EventBus,
        parameterResolverFactory: ParameterResolverFactory
    ): Repository<VideoGameStoreUser> {
        return GenericJpaRepository.builder(VideoGameStoreUser::class.java)
            .entityManagerProvider(SimpleEntityManagerProvider(entityManager))
            .parameterResolverFactory(parameterResolverFactory)
            .eventBus(eventBus)
            .identifierConverter { VideoGameStoreUserId(it) }
            .build()
    }
}

