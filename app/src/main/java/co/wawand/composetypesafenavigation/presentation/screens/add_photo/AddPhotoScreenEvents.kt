package co.wawand.composetypesafenavigation.presentation.screens.add_photo

import android.net.Uri
import androidx.camera.core.ImageCapture
import java.util.concurrent.Executor

sealed class AddPhotoScreenEvents {
    data class OnImageCaptured(val uri: Uri) : AddPhotoScreenEvents()
    data object OnFlashModeChanged : AddPhotoScreenEvents()
    data class OnTakePhoto(
        val cameraExecutor: Executor,
        val imageCapture: ImageCapture,
        val onImageCaptured: (Uri) -> Unit
    ) :
        AddPhotoScreenEvents()

    data object ClearError : AddPhotoScreenEvents()
}