package co.wawand.composetypesafenavigation.domain.usecase

import android.util.Log
import co.wawand.composetypesafenavigation.core.Constant.GENERIC_ERROR
import co.wawand.composetypesafenavigation.core.util.Resource
import co.wawand.composetypesafenavigation.domain.model.LocalPhoto
import co.wawand.composetypesafenavigation.domain.repository.PhotoRepository
import co.wawand.composetypesafenavigation.domain.repository.UserPreferencesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetPhotosUseCase @Inject constructor(
    private val photoRepository: PhotoRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) {
    operator fun invoke(): Flow<Resource<List<LocalPhoto>>> = flow {
        emit(Resource.Loading())

        runCatching {
            getUserId().let {
                if (it != null) {
                    photoRepository.getUserPhotos(it)
                } else {
                    flow { emit(Resource.Error(GENERIC_ERROR)) }
                }
            }

        }.onFailure {
            Log.d(GetAlbumsUseCase::class.simpleName, "loadPhotos error: ${it.message}")
            emit(Resource.Error(it.message ?: GENERIC_ERROR))
        }.onSuccess {
            it.collect { result ->
                when (result) {
                    is Resource.Loading -> emit(Resource.Loading())
                    is Resource.Error -> emit(Resource.Error(result.message ?: GENERIC_ERROR))
                    is Resource.Success -> emit(Resource.Success(result.data))
                }
            }
        }
    }

    private suspend fun getUserId(): Long? {
        return try {
            userPreferencesRepository.getUserId().data
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}