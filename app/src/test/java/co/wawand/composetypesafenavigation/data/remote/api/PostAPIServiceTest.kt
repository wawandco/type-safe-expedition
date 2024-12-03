package co.wawand.composetypesafenavigation.data.remote.api

import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class PostAPIServiceTest : BaseAPIServiceTest() {

    private lateinit var service: PostAPIService

    @Before
    fun setUpService() {
        service = retrofit.create(PostAPIService::class.java)
    }

    @Test
    fun `getPosts returns list of posts successfully`() = runTest {
        // Arrange
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody(
                """
                [
                    {
                        "id": 1,
                        "title": "First Post",
                        "body": "This is the first post content",
                        "userId": 10
                    },
                    {
                        "id": 2,
                        "title": "Second Post",
                        "body": "This is the second post content",
                        "userId": 11
                    }
                ]
            """.trimIndent()
            )

        mockWebServer.enqueue(mockResponse)

        // Act
        val response = service.getPosts()

        // Assert
        assertTrue(response.isSuccessful)
        val posts = response.body()
        assertEquals(2, posts?.size)

        val firstPost = posts?.get(0)
        assertEquals(1L, firstPost?.id)
        assertEquals("First Post", firstPost?.title)
        assertEquals("This is the first post content", firstPost?.body)
        assertEquals(10L, firstPost?.userId)
    }
}