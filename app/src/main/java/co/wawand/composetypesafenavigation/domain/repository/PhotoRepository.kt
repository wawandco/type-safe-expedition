package co.wawand.composetypesafenavigation.domain.repository

import co.wawand.composetypesafenavigation.core.util.Resource
import co.wawand.composetypesafenavigation.domain.model.LocalPhoto
import co.wawand.composetypesafenavigation.domain.model.RemotePhoto
import kotlinx.coroutines.flow.Flow

interface PhotoRepository {

    suspend fun getRemotePhotoDetails(id: Long): Flow<Resource<RemotePhoto>>

    suspend fun getLocalPhotoDetails(id: Long): Flow<Resource<LocalPhoto>>

    suspend fun persistUserPhoto(photo: LocalPhoto, userId: Long): Flow<Resource<Long>>

    suspend fun getUserPhotos(userId: Long): Flow<Resource<List<LocalPhoto>>>

    suspend fun getPhotoPathById(photoId: Long): Flow<Resource<String>>

    suspend fun deleteUserPhoto(photoId: Long): Flow<Resource<Boolean>>
}