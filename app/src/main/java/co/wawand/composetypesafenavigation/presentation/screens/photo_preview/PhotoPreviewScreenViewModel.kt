package co.wawand.composetypesafenavigation.presentation.screens.photo_preview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.wawand.composetypesafenavigation.core.Constant.GENERIC_ERROR
import co.wawand.composetypesafenavigation.core.util.Resource
import co.wawand.composetypesafenavigation.domain.usecase.PersistPhotoUseCase
import co.wawand.composetypesafenavigation.presentation.screens.lib.text.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class PhotoPreviewScreenViewModel @Inject constructor(
    private val persistPhotoUseCase: PersistPhotoUseCase
) : ViewModel() {
    private val viewModelState = MutableStateFlow(PhotoPreviewScreenState())
    val uiState = viewModelState.asStateFlow()

    fun onEvent(events: PhotoPreviewScreenEvents) {
        when (events) {
            is PhotoPreviewScreenEvents.LoadPhotoUri -> loadPhotoUri(events.uriString)
            is PhotoPreviewScreenEvents.OnPersistUserPhoto -> persistPhoto()
            is PhotoPreviewScreenEvents.ClearError -> clearError()
            is PhotoPreviewScreenEvents.RemovePreviewAndRedirect -> {
                redirect(
                    deleteResult = deletePreview(photoFile = events.photoFilePreview),
                    navigateTo = events.navigateTo
                )
            }
        }
    }

    private fun loadPhotoUri(uriString: String) {
        viewModelState.update {
            it.copy(photoUri = uriString)
        }
    }

    private fun persistPhoto() {
        viewModelState.value.photoUri?.let { photoUri ->
            println("persisting photo...")
            println("photoUri: $photoUri")
            viewModelScope.launch(Dispatchers.IO) {
                persistPhotoUseCase.invoke(photoUri).collect { result ->
                    println("result: $result")
                    when (result) {
                        is Resource.Loading -> viewModelState.update {
                            it.copy(isLoading = true)
                        }

                        is Resource.Error -> viewModelState.update {
                            it.copy(
                                isLoading = false,
                                error = UiText.DynamicString(result.message ?: GENERIC_ERROR)
                            )
                        }

                        is Resource.Success -> viewModelState.update {
                            it.copy(
                                isLoading = false,
                                isPhotoSaved = true
                            )
                        }
                    }
                }
            }
        } ?: run {
            viewModelState.update {
                it.copy(
                    error = UiText.DynamicString(GENERIC_ERROR)
                )
            }
        }
    }

    private fun clearError() {
        viewModelState.update {
            it.copy(error = UiText.DynamicString(""))
        }
    }

    private fun deletePreview(photoFile: File): Boolean {
        return if (photoFile.exists() && !photoFile.isDirectory) {
            photoFile.delete()
        } else false
    }

    private fun redirect(deleteResult: Boolean, navigateTo: PhotoPreviewNavigation) {
        if (deleteResult) {
            viewModelState.update {
                it.copy(navigation = navigateTo)
            }
        } else {
            viewModelState.update {
                it.copy(error = UiText.DynamicString(GENERIC_ERROR))
            }
        }
    }
}