package co.wawand.composetypesafenavigation.data.repository

import co.wawand.composetypesafenavigation.core.Constant.GENERIC_ERROR
import co.wawand.composetypesafenavigation.core.util.Resource
import co.wawand.composetypesafenavigation.data.local.database.dao.PhotoDao
import co.wawand.composetypesafenavigation.data.local.database.entity.PhotoType
import co.wawand.composetypesafenavigation.data.mapper.toDomain
import co.wawand.composetypesafenavigation.data.mapper.toEntity
import co.wawand.composetypesafenavigation.data.mapper.toPhotoDetailsEntity
import co.wawand.composetypesafenavigation.domain.model.LocalPhoto
import co.wawand.composetypesafenavigation.domain.model.RemotePhoto
import co.wawand.composetypesafenavigation.domain.repository.PhotoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.File
import javax.inject.Inject

class PhotoRepositoryImpl @Inject constructor(
    private val photoDao: PhotoDao
) : PhotoRepository {

    override suspend fun getRemotePhotoDetails(id: Long): Flow<Resource<RemotePhoto>> = flow {
        emit(Resource.Loading())

        runCatching {
            photoDao.getPhotoWithAlbumById(id)
        }.onFailure {
            it.printStackTrace()
            emit(Resource.Error(it.message ?: GENERIC_ERROR))
        }.onSuccess {
            emit(Resource.Success(it.toDomain()))
        }
    }

    override suspend fun getLocalPhotoDetails(id: Long): Flow<Resource<LocalPhoto>> = flow {
        emit(Resource.Loading())

        runCatching {
            photoDao.getPhotoById(id)
        }.onFailure {
            it.printStackTrace()
            emit(Resource.Error(it.message ?: GENERIC_ERROR))
        }.onSuccess {
            emit(Resource.Success(it.toPhotoDetailsEntity()))
        }
    }

    override suspend fun persistUserPhoto(
        photo: LocalPhoto,
        userId: Long
    ): Flow<Resource<Long>> = flow {
        emit(Resource.Loading())

        runCatching {
            photoDao.upsertUserPhoto(photo.toEntity(userId))
        }.onFailure {
            it.printStackTrace()
            emit(Resource.Error(it.message ?: GENERIC_ERROR))
        }.onSuccess {
            emit(Resource.Success(it))
        }
    }

    override suspend fun getUserPhotos(userId: Long): Flow<Resource<List<LocalPhoto>>> =
        flow {
            emit(Resource.Loading())

            runCatching {
                photoDao.getPhotosByUserId(userId)
            }.onFailure {
                it.printStackTrace()
                emit(Resource.Error(it.message ?: GENERIC_ERROR))
            }.onSuccess {
                emit(Resource.Success(it.map { photo -> photo.toPhotoDetailsEntity() }))
            }
        }

    override suspend fun getPhotoPathById(photoId: Long): Flow<Resource<String>> = flow {
        emit(Resource.Loading())

        runCatching {
            photoDao.getPhotoPathById(photoId)
        }.onFailure {
            it.printStackTrace()
            emit(Resource.Error(it.message ?: GENERIC_ERROR))
        }.onSuccess {
            emit(Resource.Success(it))
        }
    }

    override suspend fun deleteUserPhoto(photoId: Long): Flow<Resource<Boolean>> = flow {
        emit(Resource.Loading())

        runCatching {
            val photo = photoDao.getPhotoById(photoId)

            if (photo.type == PhotoType.LOCAL) {
                val file = File(photo.path!!)
                val success = file.delete()
                if (!success) {
                    emit(Resource.Error("Error deleting file"))
                    return@flow
                }
            }
            photoDao.deletePhotoById(photoId)
        }.onFailure {
            it.printStackTrace()
            emit(Resource.Error(it.message ?: GENERIC_ERROR))
        }.onSuccess {
            emit(Resource.Success(true))
        }
    }

}