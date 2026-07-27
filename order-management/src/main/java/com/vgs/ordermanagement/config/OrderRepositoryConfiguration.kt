package com.vgs.ordermanagement.config

import com.vgs.ordermanagement.model.Order
import com.vgs.ordermanagement.model.common.OrderId
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
open class OrderRepositoryConfiguration(@PersistenceContext val entityManager: EntityManager) {

    @Bean("axonOrderRepository")
    fun orderGenericJpaRepository(
        eventBus: EventBus,
        parameterResolverFactory: ParameterResolverFactory
    ): Repository<Order> {
        return GenericJpaRepository.builder(Order::class.java)
            .entityManagerProvider(SimpleEntityManagerProvider(entityManager))
            .parameterResolverFactory(parameterResolverFactory)
            .eventBus(eventBus)
            .identifierConverter { OrderId(it) }
            .build()
    }
}
