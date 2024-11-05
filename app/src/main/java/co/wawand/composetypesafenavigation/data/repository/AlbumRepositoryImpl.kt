package co.wawand.composetypesafenavigation.data.repository

import android.util.Log
import co.wawand.composetypesafenavigation.core.util.Resource
import co.wawand.composetypesafenavigation.data.local.database.dao.AlbumDao
import co.wawand.composetypesafenavigation.data.local.database.dao.PhotoDao
import co.wawand.composetypesafenavigation.data.mapper.toDBEntity
import co.wawand.composetypesafenavigation.data.mapper.toDomain
import co.wawand.composetypesafenavigation.data.remote.api.AlbumAPIService
import co.wawand.composetypesafenavigation.data.remote.api.entity.AlbumAPIEntity
import co.wawand.composetypesafenavigation.data.remote.api.entity.PhotoAPIEntity
import co.wawand.composetypesafenavigation.domain.model.Album
import co.wawand.composetypesafenavigation.domain.repository.AlbumRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AlbumRepositoryImpl @Inject constructor(
    private val albumAPIService: AlbumAPIService,
    private val albumDao: AlbumDao,
    private val photoDao: PhotoDao,
) : AlbumRepository {

    override suspend fun retrieveRemoteAlbums(): Flow<Resource<Boolean>> = flow {

        emit(Resource.Loading())

        runCatching {
            albumAPIService.getAlbums()
        }.onFailure {
            it.printStackTrace()
            emit(Resource.Error(it.message.toString()))
        }.onSuccess { response ->
            if (response.isSuccessful) {
                response.body()?.let { remoteAlbums ->
                    saveAlbums(remoteAlbums)?.let {
                        emit(Resource.Error(it))
                    } ?: emit(Resource.Success(true))
                }
            } else {
                emit(Resource.Success(false))
            }
        }
    }

    override suspend fun retrieveRemotePhotos(): Flow<Resource<Boolean>> = flow {
        emit(Resource.Loading())

        runCatching {
            albumAPIService.getPhotos()
        }.onFailure {
            it.printStackTrace()
            emit(Resource.Error(it.message.toString()))
        }.onSuccess { response ->
            if (response.isSuccessful) {
                response.body()?.let { remotePhotos ->
                    savePhotos(remotePhotos)?.let {
                        emit(Resource.Error(it))
                    } ?: emit(Resource.Success(true))
                }
            } else {
                Log.d("AlbumRepositoryImpl", "remote photos response fail: ${response.body()}")
                emit(Resource.Success(false))
            }
        }
    }

    override suspend fun retrieveLocalAlbums(): Flow<Resource<List<Album>>> = flow {
        emit(Resource.Loading())

        runCatching {
            albumDao.getAlbumsWithPhotos()
        }.onFailure {
            it.printStackTrace()
            emit(Resource.Error(it.message.toString()))
        }.onSuccess { localAlbums ->
            localAlbums.map {
                emit(Resource.Success(localAlbums.map { it.toDomain() }))
            }
        }
    }

    override suspend fun getAlbumDetails(id: Long): Flow<Resource<Album>> = flow {
        emit(Resource.Loading())

        runCatching {
            albumDao.getAlbumById(id).toDomain()
        }.onFailure {
            it.printStackTrace()
            emit(Resource.Error(it.message.toString()))
        }.onSuccess {
            emit(Resource.Success(it))
        }
    }

    private suspend fun saveAlbums(remoteAlbums: List<AlbumAPIEntity>): String? {
        return try {
            albumDao.insertAlbums(remoteAlbums.map { it.toDBEntity() })
            null
        } catch (e: Exception) {
            e.printStackTrace()
            Log.d("AlbumRepository", "saveAlbums error: ${e.message}")
            e.message
        }
    }

    private suspend fun savePhotos(remotePhotos: List<PhotoAPIEntity>): String? {
        return try {
            photoDao.insertPhotos(remotePhotos.map { it.toDBEntity() })
            null
        } catch (e: Exception) {
            e.printStackTrace()
            Log.d("AlbumRepository", "savePhotos error: ${e.message}")
            e.message
        }
    }
}