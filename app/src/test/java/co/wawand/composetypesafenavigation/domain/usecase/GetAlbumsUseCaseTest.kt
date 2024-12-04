package co.wawand.composetypesafenavigation.domain.usecase

import co.wawand.composetypesafenavigation.core.util.Resource
import co.wawand.composetypesafenavigation.domain.model.Album
import co.wawand.composetypesafenavigation.domain.repository.AlbumRepository
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class GetAlbumsUseCaseTest {

    @Mock
    private lateinit var albumRepository: AlbumRepository

    private lateinit var getAlbumsUseCase: GetAlbumsUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        getAlbumsUseCase = GetAlbumsUseCase(albumRepository)
    }

    @Test
    fun `get albums success flow`() = runTest {
        val mockAlbums = listOf(
            Album(id = 1, title = "Album 1", owner = null, photos = 20),
            Album(id = 2, title = "Album 2", owner = null, photos = 30),
            Album(id = 3, title = "Album 3", owner = null, photos = 40)
        )

        `when`(albumRepository.retrieveRemoteAlbums()).thenReturn(flow {
            emit(Resource.Loading())
            emit(Resource.Success(true))
        })

        `when`(albumRepository.retrieveRemotePhotos()).thenReturn(flow {
            emit(Resource.Loading())
            emit(Resource.Success(true))
        })

        `when`(albumRepository.retrieveLocalAlbums()).thenReturn(flow {
            emit(Resource.Loading())
            emit(Resource.Success(mockAlbums))
        })

        val results = getAlbumsUseCase().toList()

        assertTrue(results[0] is Resource.Loading)

        val successResult = results.last() as Resource.Success
        assertTrue(successResult.data?.size == 3)

        verify(albumRepository).retrieveRemoteAlbums()
        verify(albumRepository).retrieveRemotePhotos()
        verify(albumRepository).retrieveLocalAlbums()
    }

    @Test
    fun `get albums remote albums failure`() = runTest {
        `when`(albumRepository.retrieveRemoteAlbums()).thenReturn(flow {
            emit(Resource.Loading())
            emit(Resource.Error("Remote albums error"))
        })

        val results = getAlbumsUseCase().toList()

        assertTrue(results[0] is Resource.Loading)
        val errorResult = results.last()
        assertTrue(errorResult is Resource.Error)

        verify(albumRepository).retrieveRemoteAlbums()
    }

    @Test
    fun `get albums remote photos failure`() = runTest {
        `when`(albumRepository.retrieveRemoteAlbums()).thenReturn(flow {
            emit(Resource.Loading())
            emit(Resource.Success(true))
        })

        `when`(albumRepository.retrieveRemotePhotos()).thenReturn(flow {
            emit(Resource.Loading())
            emit(Resource.Error("Remote photos error"))
        })

        val results = getAlbumsUseCase().toList()

        assertTrue(results[0] is Resource.Loading)

        assertTrue(results.last() is Resource.Error)

        verify(albumRepository).retrieveRemoteAlbums()
        verify(albumRepository).retrieveRemotePhotos()
    }

    @Test
    fun `get albums returns empty list when remote albums fail`() = runTest {
        `when`(albumRepository.retrieveRemoteAlbums()).thenReturn(flow {
            emit(Resource.Loading())
            emit(Resource.Success(false))
        })

        val results = getAlbumsUseCase().toList()

        assertTrue(results[0] is Resource.Loading)

        val successResult = results.last() as Resource.Success

        assertEquals(emptyList<Album>(), successResult.data)

        verify(albumRepository).retrieveRemoteAlbums()
    }
}