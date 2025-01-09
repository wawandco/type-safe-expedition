package co.wawand.composetypesafenavigation.presentation.screens.add_photo

import android.net.Uri
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCapture.FLASH_MODE_OFF
import androidx.camera.core.ImageCapture.FLASH_MODE_ON
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.wawand.composetypesafenavigation.core.Constant.GENERIC_ERROR
import co.wawand.composetypesafenavigation.core.util.Resource
import co.wawand.composetypesafenavigation.domain.usecase.TakePhotoUseCase
import co.wawand.composetypesafenavigation.presentation.screens.lib.text.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.concurrent.Executor
import javax.inject.Inject

@HiltViewModel
class AddPhotoScreenViewModel @Inject constructor(
    private val takePhotoUseCase: TakePhotoUseCase,
) : ViewModel() {
    private val viewModelState = MutableStateFlow(AddPhotoScreenState())
    val uiState = viewModelState.asStateFlow()

    fun onEvent(event: AddPhotoScreenEvents) {
        when (event) {
            is AddPhotoScreenEvents.OnImageCaptured -> updateImageCaptured(event.uri)
            is AddPhotoScreenEvents.OnFlashModeChanged -> updateFlashMode()
            is AddPhotoScreenEvents.OnTakePhoto -> takePhoto(
                event.cameraExecutor,
                event.imageCapture,
                event.onImageCaptured
            )

            is AddPhotoScreenEvents.ClearError -> clearError()
        }
    }

    private fun updateImageCaptured(uri: Uri) {
        viewModelState.update {
            it.copy(
                showCamera = false, showPreview = true, photoUri = uri
            )
        }
    }

    private fun updateFlashMode() {
        val currentFlashMode = viewModelState.value.flashMode
        val newFlashMode = if (currentFlashMode == FLASH_MODE_OFF) FLASH_MODE_ON else FLASH_MODE_OFF
        viewModelState.update {
            it.copy(
                flashMode = newFlashMode,
                imageCapture = ImageCapture.Builder().setFlashMode(newFlashMode).build()
            )
        }
    }

    private fun takePhoto(
        executor: Executor,
        imageCapture: ImageCapture,
        onImageCaptured: (Uri) -> Unit
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            takePhotoUseCase.invoke(executor, imageCapture).collect { result ->
                when (result) {
                    is Resource.Loading -> viewModelState.update { it.copy(isLoading = true) }
                    is Resource.Error -> viewModelState.update {
                        it.copy(
                            isLoading = false,
                            error = UiText.DynamicString(result.message ?: GENERIC_ERROR)
                        )
                    }

                    is Resource.Success -> {
                        println("AddPhotoScreenViewModel, result.data: ${result.data}")
                        result.data?.let { onImageCaptured(Uri.parse(it)) }
                    }
                }
            }
        }
    }

    private fun clearError() {
        viewModelState.update {
            it.copy(error = UiText.DynamicString(""))
        }
    }
}