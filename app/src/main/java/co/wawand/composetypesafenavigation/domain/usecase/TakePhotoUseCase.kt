package co.wawand.composetypesafenavigation.domain.usecase

import android.net.Uri
import androidx.camera.core.ImageCapture
import co.wawand.composetypesafenavigation.core.Constant.GENERIC_ERROR
import co.wawand.composetypesafenavigation.core.util.Resource
import co.wawand.composetypesafenavigation.domain.repository.CameraRepository
import co.wawand.composetypesafenavigation.domain.repository.UserPreferencesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.concurrent.Executor
import javax.inject.Inject

class TakePhotoUseCase @Inject constructor(
    private val cameraRepository: CameraRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) {
    operator fun invoke(executor: Executor, imageCapture: ImageCapture): Flow<Resource<String>> =
        flow {
            emit(Resource.Loading())

            runCatching {
                getUserId().let {
                    if (it != null) {
                        cameraRepository.capturePhoto(
                            prefix = "user_$it",
                            executor = executor,
                            imageCapture = imageCapture
                        )
                    } else {
                        flow { emit(Resource.Error(GENERIC_ERROR)) }
                    }
                }
            }.onFailure {
                it.printStackTrace()
                emit(Resource.Error(it.message ?: GENERIC_ERROR))
            }.onSuccess {
                it.collect { result ->
                    when (result) {
                        is Resource.Loading -> emit(Resource.Loading())
                        is Resource.Error -> emit(Resource.Error(result.message ?: GENERIC_ERROR))
                        is Resource.Success -> {
                            emit(Resource.Success(result.data))
                        }
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