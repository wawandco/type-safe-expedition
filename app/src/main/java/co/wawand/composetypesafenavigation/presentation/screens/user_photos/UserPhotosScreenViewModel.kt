package co.wawand.composetypesafenavigation.presentation.screens.user_photos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.wawand.composetypesafenavigation.core.Constant.GENERIC_ERROR
import co.wawand.composetypesafenavigation.core.util.Resource
import co.wawand.composetypesafenavigation.domain.model.BasePhoto
import co.wawand.composetypesafenavigation.domain.usecase.DeletePhotosUseCase
import co.wawand.composetypesafenavigation.domain.usecase.GetPhotosUseCase
import co.wawand.composetypesafenavigation.presentation.screens.lib.text.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserPhotosScreenViewModel @Inject constructor(
    private val getPhotosUseCase: GetPhotosUseCase,
    private val deletePhotosUseCase: DeletePhotosUseCase
) : ViewModel() {
    private val viewModelState = MutableStateFlow(UserPhotosScreenState())
    val uiState = viewModelState.asStateFlow()

    fun onEvent(event: UserPhotosEvents) {
        when (event) {
            is UserPhotosEvents.OnDeleteClicked -> deletePhotos(event.photosId)
            is UserPhotosEvents.OnGoToCameraClicked -> {}
            is UserPhotosEvents.SetCameraPermission -> {
                setCameraPermission(event.hasCameraPermission)
            }

            is UserPhotosEvents.HandleCameraPermissionError -> showCameraPermissionError(event.message)

            is UserPhotosEvents.ClearError -> viewModelState.update {
                it.copy(
                    error = UiText.DynamicString(
                        ""
                    )
                )
            }

            is UserPhotosEvents.OnPhotoClicked -> viewModelState.update { it.copy(selectedPhoto = event.photo) }

            is UserPhotosEvents.OnHidePhotoPreview -> viewModelState.update {
                it.copy(
                    selectedPhoto = null
                )
            }

            is UserPhotosEvents.UpdatePhotoSelection -> viewModelState.update {
                it.copy(
                    selectedIds = event.selectedIds
                )
            }
        }
    }

    init {
        loadUserPhotos()
    }

    private fun setCameraPermission(hasCameraPermission: Boolean) {
        viewModelState.update { it.copy(hasCameraPermission = hasCameraPermission) }
    }

    private fun loadUserPhotos() {
        viewModelScope.launch(Dispatchers.IO) {
            getPhotosUseCase.invoke().collect { result ->
                when (result) {
                    is Resource.Loading -> viewModelState.update { it.copy(isLoading = true) }
                    is Resource.Success -> viewModelState.update {
                        it.copy(
                            photos = result.data ?: emptyList(), isLoading = false
                        )
                    }

                    is Resource.Error -> viewModelState.update {
                        it.copy(
                            isLoading = false,
                            error = UiText.DynamicString(result.message ?: GENERIC_ERROR),
                        )
                    }
                }
            }
        }
    }

    private fun showCameraPermissionError(message: UiText) {
        viewModelState.update { it.copy(error = message) }
    }

    private fun deletePhotos(photoIds: Set<Long>) {
        viewModelScope.launch(Dispatchers.IO) {
            deletePhotosUseCase.invoke(photoIds).collect { result ->
                when (result) {
                    is Resource.Loading -> viewModelState.update { it.copy(isLoading = true) }
                    is Resource.Error -> viewModelState.update {
                        it.copy(
                            isLoading = false,
                            error = UiText.DynamicString(result.message ?: GENERIC_ERROR),
                        )
                    }

                    is Resource.Success -> {
                        viewModelState.update {
                            it.copy(
                                isLoading = false,
                                selectedIds = emptySet()
                            )
                        }
                        loadUserPhotos()
                    }
                }
            }
        }
    }
}