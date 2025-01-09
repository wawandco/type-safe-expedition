package co.wawand.composetypesafenavigation.domain.usecase

import co.wawand.composetypesafenavigation.core.Constant.GENERIC_ERROR
import co.wawand.composetypesafenavigation.core.util.Resource
import co.wawand.composetypesafenavigation.domain.repository.PhotoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DeletePhotosUseCase @Inject constructor(
    private val photoRepository: PhotoRepository,
) {
    operator fun invoke(photoIds: Set<Long>): Flow<Resource<Boolean>> = flow {
        emit(Resource.Loading())

        runCatching {
            photoIds.forEach { photoId ->
                photoRepository.deleteUserPhoto(photoId).collect { result ->
                    when (result) {
                        is Resource.Loading -> emit(Resource.Loading())
                        is Resource.Error -> emit(Resource.Error(result.message ?: GENERIC_ERROR))
                        is Resource.Success -> emit(Resource.Success(true))
                    }
                }
            }
        }.onFailure {
            it.printStackTrace()
            emit(Resource.Error(it.message ?: GENERIC_ERROR))
        }
    }
}