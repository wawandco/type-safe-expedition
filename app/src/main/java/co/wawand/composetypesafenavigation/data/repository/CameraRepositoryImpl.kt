package co.wawand.composetypesafenavigation.data.repository

import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import co.wawand.composetypesafenavigation.core.Constant.GENERIC_ERROR
import co.wawand.composetypesafenavigation.core.util.Resource
import co.wawand.composetypesafenavigation.di.IoDispatcher
import co.wawand.composetypesafenavigation.domain.repository.CameraRepository
import co.wawand.composetypesafenavigation.domain.service.FileService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.concurrent.Executor
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class CameraRepositoryImpl @Inject constructor(
    private val fileService: FileService,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : CameraRepository {

    override suspend fun capturePhoto(
        prefix: String,
        executor: Executor,
        imageCapture: ImageCapture
    ): Flow<Resource<String>> =
        flow {
            try {
                val photoFile = fileService.generatePhotoFile(prefix)

                suspendCancellableCoroutine { continuation ->
                    val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

                    imageCapture.takePicture(
                        outputOptions,
                        executor,
                        object : ImageCapture.OnImageSavedCallback {
                            override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                                continuation.resume(photoFile.absolutePath)
                            }

                            override fun onError(exception: ImageCaptureException) {
                                continuation.resumeWithException(exception)
                            }
                        }
                    )
                }.let { uri ->
                    emit(Resource.Success(uri))
                }
            } catch (e: Exception) {
                emit(Resource.Error(e.message ?: GENERIC_ERROR))
            }
        }.flowOn(dispatcher)
}