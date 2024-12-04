package co.wawand.composetypesafenavigation.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import co.wawand.composetypesafenavigation.core.Constant.GENERIC_ERROR
import co.wawand.composetypesafenavigation.core.util.Resource
import co.wawand.composetypesafenavigation.domain.model.AlbumWithPhotos
import co.wawand.composetypesafenavigation.domain.model.Photo
import co.wawand.composetypesafenavigation.domain.repository.AlbumRepository
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.anyLong
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetAlbumDetailsUseCaseTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var albumRepository: AlbumRepository

    private lateinit var getAlbumDetailsUseCase: GetAlbumDetailsUseCase

    @Before
    fun setUp() {
        getAlbumDetailsUseCase = GetAlbumDetailsUseCase(albumRepository)
    }

    @Test
    fun `getAlbumDetails emits loading and success states`() = runTest {
        // Arrange
        val albumId = 1L
        val mockAlbumWithPhotos = AlbumWithPhotos(
            id = albumId,
            title = "Album Title",
            owner = null,
            photos = listOf(
                Photo(
                    id = 1L,
                    title = "Photo Title",
                    thumbnailUrl = "https://example.com/thumbnail.jpg",
                    url = "https://example.com/photo.jpg",
                    album = albumId.toString(),
                ),
                Photo(
                    id = 2L,
                    title = "Photo Title",
                    thumbnailUrl = "https://example.com/thumbnail.jpg",
                    url = "https://example.com/photo.jpg",
                    album = albumId.toString(),
                )
            )
        )

        `when`(albumRepository.getAlbumDetailsWithPhotos(albumId)).thenReturn(flow {
            emit(Resource.Loading())
            emit(Resource.Success(mockAlbumWithPhotos))
        })

        val results = getAlbumDetailsUseCase(albumId).toList()

        assertTrue(results[0] is Resource.Loading)

        val successResult = results.last() as Resource.Success
        assertTrue(successResult.data?.id == albumId)
        assertEquals(2, successResult.data?.photos?.size)

        // Verify repository method was called
        verify(albumRepository).getAlbumDetailsWithPhotos(anyLong())
    }

    @Test
    fun `getAlbumDetails emits loading and error states`() = runTest {
        // Arrange
        val albumId = 2L

        `when`(albumRepository.getAlbumDetailsWithPhotos(albumId)).thenReturn(flow {
            emit(Resource.Loading())
            emit(Resource.Error(GENERIC_ERROR))
        })

        // Act & Assert
        val results = getAlbumDetailsUseCase(albumId).toList()

        assertTrue(results[0] is Resource.Loading)

        val errorResult = results.last() as Resource.Error
        assertTrue(errorResult.message == GENERIC_ERROR)
        assertEquals(null, errorResult.data)

        // Verify repository method was called
        verify(albumRepository).getAlbumDetailsWithPhotos(anyLong())
    }

    @Test
    fun `getAlbumDetails handles repository exception`() = runTest {
        // Arrange
        val albumId = 3L
        val exception = RuntimeException("Repository failure")

        `when`(albumRepository.getAlbumDetailsWithPhotos(albumId)).thenThrow(exception)

        // Act & Assert
        val results = getAlbumDetailsUseCase(albumId).toList()

        assertTrue(results[0] is Resource.Loading)

        val errorResult = results.last() as Resource.Error
        assertTrue(errorResult.message == "Repository failure")
        assertEquals(null, errorResult.data)

        // Verify repository method was called
        verify(albumRepository).getAlbumDetailsWithPhotos(anyLong())
    }
}