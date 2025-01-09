package co.wawand.composetypesafenavigation.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import co.wawand.composetypesafenavigation.core.util.Resource
import co.wawand.composetypesafenavigation.data.local.database.dao.PhotoDao
import co.wawand.composetypesafenavigation.data.local.database.entity.AlbumEntity
import co.wawand.composetypesafenavigation.data.local.database.entity.PhotoEntity
import co.wawand.composetypesafenavigation.data.local.database.entity.PhotoType
import co.wawand.composetypesafenavigation.data.local.database.entity.PhotoWithAlbum
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RemotePhotoRepositoryImplTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var photoDao: PhotoDao

    private lateinit var repository: PhotoRepositoryImpl

    @Before
    fun setup() {
        repository = PhotoRepositoryImpl(photoDao)
    }

    @Test
    fun `getPhotoDetails returns successful photo when photo exists`() = runTest {
        // Arrange
        val photoId = 1L
        val photoWithAlbum = PhotoWithAlbum(
            photoEntity = PhotoEntity(
                id = photoId,
                albumId = 1,
                type = PhotoType.REMOTE,
                title = "Test Photo",
                url = "http://test.com/photo",
                thumbnailUrl = "http://test.com/thumb"
            ), albumEntity = AlbumEntity(
                id = 1, title = "Test Album", ownerId = 1
            )
        )

        `when`(photoDao.getPhotoWithAlbumById(photoId)).thenReturn(photoWithAlbum)

        // Act
        val results = repository.getRemotePhotoDetails(photoId).toList()

        // Assert
        assertEquals(2, results.size)
        assertTrue(results[0] is Resource.Loading)

        val successResult = results[1] as Resource.Success
        assertEquals(photoId, successResult.data?.id)
        assertEquals("Test Photo", successResult.data?.title)
    }

    @Test
    fun `getPhotoDetails handles database error`() = runTest {
        // Arrange
        val photoId = 1L
        `when`(photoDao.getPhotoWithAlbumById(photoId)).thenThrow(RuntimeException("Database error"))

        // Act
        val results = repository.getRemotePhotoDetails(photoId).toList()

        // Assert
        assertEquals(2, results.size)
        assertTrue(results[0] is Resource.Loading)

        val errorResult = results[1] as Resource.Error
        assertTrue(errorResult.message!!.contains("Database error"))
    }
}