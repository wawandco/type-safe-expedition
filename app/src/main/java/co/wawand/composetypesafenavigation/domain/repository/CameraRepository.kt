package co.wawand.composetypesafenavigation.domain.repository

import androidx.camera.core.ImageCapture
import co.wawand.composetypesafenavigation.core.util.Resource
import kotlinx.coroutines.flow.Flow
import java.util.concurrent.Executor

interface CameraRepository {
    suspend fun capturePhoto(
        prefix: String,
        executor: Executor,
        imageCapture: ImageCapture
    ): Flow<Resource<String>>
}