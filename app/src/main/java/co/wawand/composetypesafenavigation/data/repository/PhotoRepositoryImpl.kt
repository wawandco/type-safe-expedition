package co.wawand.composetypesafenavigation.data.repository

import co.wawand.composetypesafenavigation.core.Constant.GENERIC_ERROR
import co.wawand.composetypesafenavigation.core.util.Resource
import co.wawand.composetypesafenavigation.data.local.database.dao.PhotoDao
import co.wawand.composetypesafenavigation.data.mapper.toDomain
import co.wawand.composetypesafenavigation.domain.model.Photo
import co.wawand.composetypesafenavigation.domain.repository.PhotoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PhotoRepositoryImpl @Inject constructor(
    private val photoDao: PhotoDao
) : PhotoRepository {
    override suspend fun getPhotoDetails(id: Long): Flow<Resource<Photo>> = flow {
        emit(Resource.Loading())

        runCatching {
            photoDao.getPhotoById(id)
        }.onFailure {
            it.printStackTrace()
            emit(Resource.Error(it.message ?: GENERIC_ERROR))
        }.onSuccess {
            emit(Resource.Success(it.toDomain()))
        }
    }

}