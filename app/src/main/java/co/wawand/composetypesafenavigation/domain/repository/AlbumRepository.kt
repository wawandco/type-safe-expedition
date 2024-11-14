package co.wawand.composetypesafenavigation.domain.repository

import co.wawand.composetypesafenavigation.core.util.Resource
import co.wawand.composetypesafenavigation.domain.model.Album
import co.wawand.composetypesafenavigation.domain.model.AlbumWithPhotos
import kotlinx.coroutines.flow.Flow

interface AlbumRepository {

    suspend fun retrieveRemoteAlbums(): Flow<Resource<Boolean>>

    suspend fun retrieveRemotePhotos(): Flow<Resource<Boolean>>

    suspend fun retrieveLocalAlbums(): Flow<Resource<List<Album>>>

    suspend fun getAlbumDetails(id: Long): Flow<Resource<Album>>

    suspend fun getAlbumDetailsWithPhotos(id: Long): Flow<Resource<AlbumWithPhotos>>
}