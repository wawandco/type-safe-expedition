package co.wawand.composetypesafenavigation.data.remote.api

import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class AlbumAPIServiceTest : BaseAPIServiceTest() {

    private lateinit var service: AlbumAPIService

    @Before
    fun setUpService() {
        service = retrofit.create(AlbumAPIService::class.java)
    }

    @Test
    fun `getAlbums returns list of albums successfully`() = runTest {
        // Arrange
        val mockResponse = MockResponse().setResponseCode(200).setBody(
            """
                [
                    {
                        "id": 1,
                        "title": "First Album",
                        "userId": 10
                    },
                    {
                        "id": 2,
                        "title": "Second Album",
                        "userId": 11
                    }
                ]
            """.trimIndent()
        )
        mockWebServer.enqueue(mockResponse)

        // Act
        val response = service.getAlbums()

        // Assert
        assertTrue(response.isSuccessful)
        val albums = response.body()
        assertEquals(2, albums?.size)

        val firstAlbum = albums?.get(0)
        assertEquals(1L, firstAlbum?.id)
        assertEquals("First Album", firstAlbum?.title)
        assertEquals(10L, firstAlbum?.userId)
    }

    @Test
    fun `getPhotos returns list of photos successfully`() = runTest {
        // Arrange
        val mockResponse = MockResponse().setResponseCode(200).setBody(
            """
                [
                    {
                        "id": 1,
                        "title": "First Photo",
                        "url": "https://example.com/photo1",
                        "thumbnailUrl": "https://example.com/thumb1",
                        "albumId": 10
                    },
                    {
                        "id": 2,
                        "title": "Second Photo",
                        "url": "https://example.com/photo2",
                        "thumbnailUrl": "https://example.com/thumb2",
                        "albumId": 11
                    }
                ]
            """.trimIndent()
        )

        mockWebServer.enqueue(mockResponse)

        // Act
        val response = service.getPhotos()

        // Assert
        assertTrue(response.isSuccessful)
        val photos = response.body()
        assertEquals(2, photos?.size)

        val firstPhoto = photos?.get(0)
        assertEquals(1L, firstPhoto?.id)
        assertEquals("First Photo", firstPhoto?.title)
        assertEquals("https://example.com/photo1", firstPhoto?.url)
        assertEquals("https://example.com/thumb1", firstPhoto?.thumbnailUrl)
        assertEquals(10L, firstPhoto?.albumId)
    }

}