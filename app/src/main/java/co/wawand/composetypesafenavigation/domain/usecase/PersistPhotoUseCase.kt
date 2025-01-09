package co.wawand.composetypesafenavigation.domain.usecase

import co.wawand.composetypesafenavigation.core.Constant.GENERIC_ERROR
import co.wawand.composetypesafenavigation.core.util.Resource
import co.wawand.composetypesafenavigation.domain.model.LocalPhoto
import co.wawand.composetypesafenavigation.domain.repository.PhotoRepository
import co.wawand.composetypesafenavigation.domain.repository.UserPreferencesRepository
import co.wawand.composetypesafenavigation.domain.service.FileService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.File
import javax.inject.Inject

class PersistPhotoUseCase @Inject constructor(
    private val photoRepository: PhotoRepository,
    private val userPreferencesRepository: UserPreferencesRepository,
) {
    operator fun invoke(photoUriString: String): Flow<Resource<Boolean>> = flow {
        emit(Resource.Loading())

        runCatching {
            getUserId()?.let {
                val photoFile = File(photoUriString)

                val details = LocalPhoto(
                    id = 0,
                    title = photoFile.name,
                    path = photoUriString,
                    size = photoFile.length(),
                    lastModified = photoFile.lastModified()
                )
                photoRepository.persistUserPhoto(details, it)
            } ?: flow { emit(Resource.Error(GENERIC_ERROR)) }
        }.onFailure {
            it.printStackTrace()
            emit(Resource.Error(it.message ?: GENERIC_ERROR))
        }.onSuccess {
            it.collect { result ->
                when (result) {
                    is Resource.Loading -> emit(Resource.Loading())
                    is Resource.Error -> emit(Resource.Error(result.message ?: GENERIC_ERROR))
                    is Resource.Success -> emit(Resource.Success(true))
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