package co.wawand.composetypesafenavigation.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import co.wawand.composetypesafenavigation.core.util.Resource
import co.wawand.composetypesafenavigation.data.local.database.dao.AuthorDao
import co.wawand.composetypesafenavigation.data.remote.api.AuthorAPIService
import co.wawand.composetypesafenavigation.data.remote.api.entity.AuthorAPIEntity
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyList
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response

@RunWith(MockitoJUnitRunner::class)
class AuthorRepositoryImplTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var authorAPIService: AuthorAPIService

    @Mock
    private lateinit var authorDao: AuthorDao

    private lateinit var repository: AuthorRepositoryImpl

    @Before
    fun setup() {
        repository = AuthorRepositoryImpl(authorAPIService, authorDao)
    }

    @Test
    fun `retrieveRemoteAuthors success`() = runTest {
        val mockAuthors = listOf(
            AuthorAPIEntity(1, "John", "johnd", "john@example.com"),
            AuthorAPIEntity(2, "Jane", "janes", "jane@example.com")
        )
        `when`(authorAPIService.getAuthors()).thenReturn(Response.success(mockAuthors))

        val results = repository.retrieveRemoteAuthors().toList()

        // Check the sequence of states
        assertEquals(2, results.size)
        assertTrue(results[0] is Resource.Loading)

        val successResult = results[1] as Resource.Success
        assertTrue(successResult.data == true)

        verify(authorDao).insertAuthors(anyList())
    }

    @Test
    fun `retrieveRemoteAuthors handles network connection error`() = runTest {
        `when`(authorAPIService.getAuthors()).thenThrow(RuntimeException("No internet connection"))

        val results = repository.retrieveRemoteAuthors().toList()

        assertEquals(2, results.size)
        assertTrue(results[0] is Resource.Loading)

        val errorResult = results[1] as Resource.Error
        assertTrue(errorResult.message?.contains("No internet connection")!!)
    }

    @Test
    fun `retrieveRemoteAuthors handles API error response`() = runTest {
        val errorResponse: Response<List<AuthorAPIEntity>> = Response.error(
            404,
            "Not Found".toResponseBody(null)
        )
        `when`(authorAPIService.getAuthors()).thenReturn(errorResponse)

        val results = repository.retrieveRemoteAuthors().toList()

        assertEquals(2, results.size)
        assertTrue(results[0] is Resource.Loading)

        // Since your current implementation emits Success(false) for non-successful responses
        val successResult = results[1] as Resource.Success
        assertTrue(successResult.data == false)
    }
}