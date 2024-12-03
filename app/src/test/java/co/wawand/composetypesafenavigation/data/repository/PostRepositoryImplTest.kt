package co.wawand.composetypesafenavigation.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import co.wawand.composetypesafenavigation.core.util.Resource
import co.wawand.composetypesafenavigation.data.local.database.dao.PostDao
import co.wawand.composetypesafenavigation.data.local.database.entity.AuthorEntity
import co.wawand.composetypesafenavigation.data.local.database.entity.PostAndAuthor
import co.wawand.composetypesafenavigation.data.local.database.entity.PostEntity
import co.wawand.composetypesafenavigation.data.mapper.toDBEntity
import co.wawand.composetypesafenavigation.data.remote.api.PostAPIService
import co.wawand.composetypesafenavigation.data.remote.api.entity.PostAPIEntity
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify
import retrofit2.Response

@RunWith(MockitoJUnitRunner::class)
class PostRepositoryImplTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var postAPIService: PostAPIService

    @Mock
    private lateinit var postDao: PostDao

    private lateinit var repository: PostRepositoryImpl

    @Before
    fun setup() {
        repository = PostRepositoryImpl(postAPIService, postDao)
    }

    @Test
    fun `retrieveRemotePosts handles successful API call`() = runTest {
        // Arrange
        val mockPosts = listOf(
            PostAPIEntity(1, "Title 1", "Body 1", 1),
            PostAPIEntity(2, "Title 2", "Body 2", 1)
        )
        `when`(postAPIService.getPosts()).thenReturn(Response.success(mockPosts))

        // Act
        val results = repository.retrieveRemotePosts().toList()

        // Assert
        assertEquals(2, results.size)
        assertTrue(results[0] is Resource.Loading)

        val successResult = results[1] as Resource.Success
        assertTrue(successResult.data == true)

        // Verify posts were inserted into local database
        verify(postDao).insertPosts(mockPosts.map { it.toDBEntity() })
    }

    @Test
    fun `retrieveRemotePosts handles API error`() = runTest {
        // Arrange
        val errorResponse: Response<List<PostAPIEntity>> = Response.error(
            404,
            "Not Found".toResponseBody(null)
        )
        `when`(postAPIService.getPosts()).thenReturn(errorResponse)

        // Act
        val results = repository.retrieveRemotePosts().toList()

        // Assert
        assertEquals(2, results.size)
        assertTrue(results[0] is Resource.Loading)

        val successResult = results[1] as Resource.Success
        assertEquals(false, successResult.data)
    }

    @Test
    fun `retrieveRemotePosts handles network exception`() = runTest {
        // Arrange
        `when`(postAPIService.getPosts()).thenThrow(RuntimeException("Network error"))

        // Act
        val results = repository.retrieveRemotePosts().toList()

        // Assert
        assertEquals(2, results.size)
        assertTrue(results[0] is Resource.Loading)

        val errorResult = results[1] as Resource.Error
        assertTrue(errorResult.message!!.contains("Network error"))
    }

    @Test
    fun `retrieveLocalPosts returns successful result`() = runTest {
        // Arrange
        val authorEntity =
            AuthorEntity(1, "john doe", "johndoe", "john.archibald.campbell@example-pet-store.com")
        val mockPostEntities = listOf(
            PostAndAuthor(
                PostEntity(1, "Title 1", "Body 1", 1),
                authorEntity
            ),
            PostAndAuthor(
                PostEntity(2, "Title 2", "Body 2", 1),
                authorEntity
            )
        )

        `when`(postDao.getPosts()).thenReturn(mockPostEntities)

        // Act
        val results = repository.retrieveLocalPosts().toList()

        // Assert
        assertEquals(2, results.size)
        assertTrue(results[0] is Resource.Loading)

        val successResult = results[1] as Resource.Success
        assertEquals(2, successResult.data?.size)
    }
}