package co.wawand.composetypesafenavigation.data.remote.api

import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class AuthorAPIServiceTest : BaseAPIServiceTest() {

    private lateinit var service: AuthorAPIService

    @Before
    fun setUpService() {
        service = retrofit.create(AuthorAPIService::class.java)
    }

    @Test
    fun `getAuthors returns list of authors successfully`() = runTest {
        // Arrange
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody(
                """
                [
                    {
                        "id": 1,
                        "name": "John Doe",
                        "username": "johnd",
                        "email": "john@example.com"
                    },
                    {
                        "id": 2,
                        "name": "Jane Smith",
                        "username": "janes",
                        "email": "jane@example.com"
                    }
                ]
            """.trimIndent()
            )
        mockWebServer.enqueue(mockResponse)

        // Act
        val response = service.getAuthors()

        // Assert
        assertTrue(response.isSuccessful)
        val authors = response.body()
        assertEquals(2, authors?.size)

        val firstAuthor = authors?.get(0)
        assertEquals(1L, firstAuthor?.id)
        assertEquals("John Doe", firstAuthor?.name)
        assertEquals("johnd", firstAuthor?.username)
        assertEquals("john@example.com", firstAuthor?.email)
    }
}