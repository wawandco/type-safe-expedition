package co.wawand.composetypesafenavigation.presentation.screens.add_photo

import android.net.Uri
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCapture.FLASH_MODE_OFF
import androidx.camera.core.ImageCapture.FLASH_MODE_ON
import co.wawand.composetypesafenavigation.presentation.screens.lib.text.UiText

data class AddPhotoScreenState(
    val isLoading: Boolean = false,
    val showCamera: Boolean = true,
    val showPreview: Boolean = false,
    val photoUri: Uri? = null,
    val flashMode: Int = FLASH_MODE_ON,
    val imageCapture: ImageCapture = ImageCapture.Builder().setFlashMode(FLASH_MODE_OFF).build(),
    val error: UiText = UiText.DynamicString(""),
)