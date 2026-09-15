package video.game.store.user.tests

import au.com.dius.pact.consumer.MockServer
import au.com.dius.pact.consumer.Pact
import au.com.dius.pact.consumer.dsl.PactDslWithProvider
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt
import au.com.dius.pact.consumer.junit5.PactTestFor
import au.com.dius.pact.model.RequestResponsePact
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.web.client.RestTemplate

@ExtendWith(PactConsumerTestExt::class)
@PactTestFor(
    providerName = "VideoGameStoreUser",
    hostInterface = "localhost"
)
class PactUserConsumerTest {

    @Pact(consumer = "user_api_consumer")
    fun userInfoPact(
        builder: PactDslWithProvider
    ): RequestResponsePact {
        return builder
            .given("user exists")
            .uponReceiving("request for user information")
            .path("/api/user/user-info")
            .query("userId=VideoGameStoreUser%3Atest-user-123")
            .method("GET")
            .willRespondWith()
            .status(200)
            .headers(
                mapOf(
                    "Content-Type" to "application/json"
                )
            )
            .body(
                """
                {
                  "id": {
                    "value": "VideoGameStoreUser:test-user-123"
                  },
                  "email": {
                    "value": "john.doe@example.com"
                  },
                  "password": {
                    "value": "Password1!"
                  },
                  "fullName": {
                    "value": "John Doe"
                  },
                  "shippingAddress": {
                    "value": "Main Street br 12 1000"
                  },
                  "age": {
                    "value": 25
                  },
                  "gender": {
                    "value": "male"
                  },
                  "role": "CUSTOMER",
                  "label": {
                    "value": "John Doe"
                  },
                  "entityType": "VideoGameStoreUserView",
                  "dateCreated": null,
                  "archived": false
                }
                """.trimIndent()
            )
            .toPact()
    }

    @Test
    @PactTestFor(
        pactMethod = "userInfoPact",
        port = "9999"
    )
    fun userInfoWorks(mockServer: MockServer) {
        val restTemplate = RestTemplate()

        val response = restTemplate.getForEntity(
            "${mockServer.getUrl()}/api/user/user-info" +
                    "?userId=VideoGameStoreUser%3Atest-user-123",
            String::class.java
        )

        assertEquals(200, response.statusCode.value())
        assertNotNull(response.body)

        val body = response.body!!

        assertEquals(
            true,
            body.contains("VideoGameStoreUser:test-user-123")
        )

        assertEquals(
            true,
            body.contains("john.doe@example.com")
        )
    }
}
