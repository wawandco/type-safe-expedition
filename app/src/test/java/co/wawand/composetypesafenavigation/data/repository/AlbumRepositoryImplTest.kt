package co.wawand.composetypesafenavigation.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import co.wawand.composetypesafenavigation.core.util.Resource
import co.wawand.composetypesafenavigation.data.local.database.dao.AlbumDao
import co.wawand.composetypesafenavigation.data.local.database.dao.PhotoDao
import co.wawand.composetypesafenavigation.data.mapper.toDBEntity
import co.wawand.composetypesafenavigation.data.remote.api.AlbumAPIService
import co.wawand.composetypesafenavigation.data.remote.api.entity.AlbumAPIEntity
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
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.verify
import org.robolectric.RobolectricTestRunner
import org.robolectric.shadows.ShadowLog
import retrofit2.Response

@RunWith(RobolectricTestRunner::class)
class AlbumRepositoryImplTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var albumAPIService: AlbumAPIService

    @Mock
    private lateinit var albumDao: AlbumDao

    @Mock
    private lateinit var photoDao: PhotoDao

    private lateinit var repository: AlbumRepositoryImpl

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        repository = AlbumRepositoryImpl(albumAPIService, albumDao, photoDao)
        ShadowLog.stream = System.out
    }

    @Test
    fun `retrieveRemoteAlbums handles successful API call`() = runTest {
        // Arrange
        val mockAlbums = listOf(
            AlbumAPIEntity(1, "Album 1", 1),
            AlbumAPIEntity(2, "Album 2", 1)
        )
        `when`(albumAPIService.getAlbums()).thenReturn(Response.success(mockAlbums))
        `when`(albumDao.insertAlbums(mockAlbums.map { it.toDBEntity() })).thenReturn(Unit)

        // Act
        val results = repository.retrieveRemoteAlbums().toList()

        // Assert
        assertEquals(2, results.size)
        assertTrue(results[0] is Resource.Loading)

        val successResult = results[1] as Resource.Success
        assertTrue(successResult.data == true)

        // Verify albums were inserted into local database
        verify(albumDao).insertAlbums(mockAlbums.map { it.toDBEntity() })
    }

    @Test
    fun `retrieveRemoteAlbums handles API error`() = runTest {
        // Arrange
        val errorResponse: Response<List<AlbumAPIEntity>> = Response.error(
            404,
            "Not Found".toResponseBody(null)
        )
        `when`(albumAPIService.getAlbums()).thenReturn(errorResponse)

        // Act
        val results = repository.retrieveRemoteAlbums().toList()

        // Assert
        assertEquals(2, results.size)
        assertTrue(results[0] is Resource.Loading)

        val successResult = results[1] as Resource.Success
        assertEquals(false, successResult.data)
    }

    @Test
    fun `retrieveRemoteAlbums handles save albums error`() = runTest {
        // Arrange
        val mockAlbums = listOf(
            AlbumAPIEntity(1, "Album 1", 1),
            AlbumAPIEntity(2, "Album 2", 1)
        )
        val errorMessage = "Save error"
        `when`(albumAPIService.getAlbums()).thenReturn(Response.success(mockAlbums))
        `when`(albumDao.insertAlbums(mockAlbums.map { it.toDBEntity() })).thenThrow(
            RuntimeException(errorMessage)
        )

        // Act
        val results = repository.retrieveRemoteAlbums().toList()

        // Assert
        assertEquals(2, results.size)
        assertTrue(results[0] is Resource.Loading)

        val errorResult = results[1] as Resource.Error
        assertTrue(errorResult.message!!.contains(errorMessage))
    }
}