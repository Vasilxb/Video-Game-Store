package video.game.store.user.tests

import au.com.dius.pact.provider.junitsupport.Provider
import au.com.dius.pact.provider.junitsupport.State
import au.com.dius.pact.provider.junitsupport.loader.PactFolder
import au.com.dius.pact.provider.junit5.HttpTestTarget
import au.com.dius.pact.provider.junit5.PactVerificationContext
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestTemplate
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.`when`
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.bean.override.mockito.MockitoBean

import video.game.store.user.model.common.*
import video.game.store.user.model.enums.Role
import video.game.store.user.model.views.VideoGameStoreUserView
import video.game.store.user.services.UserViewReadService

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@ActiveProfiles("test")
@Provider("VideoGameStoreUser")
@PactFolder("pacts")
class PactUserProviderTest {

    @LocalServerPort
    var port: Int = 0

    @MockitoBean
    lateinit var userViewReadService: UserViewReadService

    @BeforeEach
    fun before(context: PactVerificationContext) {
        context.target = HttpTestTarget("localhost", port)
    }

    @TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider::class)
    fun pactVerificationTestTemplate(context: PactVerificationContext) {
        context.verifyInteraction()
    }

    @State("user exists")
    fun userExists() {
        val userId = VideoGameStoreUserId("test-user-123")

        val user = VideoGameStoreUserView(
            id = userId,
            email = Email("john.doe@example.com"),
            password = Password("Password1!"),
            fullName = FullName("John Doe"),
            shippingAddress = ShippingAddress("Main Street br 12 1000"),
            age = Age(25),
            gender = Gender("male"),
            role = Role.CUSTOMER
        )

        `when`(userViewReadService.findById(userId))
            .thenReturn(user)
    }
}
