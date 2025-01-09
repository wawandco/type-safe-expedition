package co.wawand.composetypesafenavigation.presentation.screens.details.album

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.wawand.composetypesafenavigation.core.Constant.GENERIC_ERROR
import co.wawand.composetypesafenavigation.core.util.Resource
import co.wawand.composetypesafenavigation.domain.model.BasePhoto
import co.wawand.composetypesafenavigation.domain.usecase.DeletePhotosUseCase
import co.wawand.composetypesafenavigation.domain.usecase.GetAlbumDetailsUseCase
import co.wawand.composetypesafenavigation.presentation.screens.lib.text.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlbumDetailsScreenViewModel @Inject constructor(
    private val getAlbumDetailsUseCase: GetAlbumDetailsUseCase,
    private val deletePhotosUseCase: DeletePhotosUseCase
) : ViewModel() {

    private val viewModelState = MutableStateFlow(AlbumDetailsScreenState())
    val uiState = viewModelState.asStateFlow()

    fun onEvent(event: AlbumDetailsScreenEvents) {
        when (event) {
            is AlbumDetailsScreenEvents.LoadAlbumDetails -> getAlbumDetails(event.albumId)
            is AlbumDetailsScreenEvents.OnPhotoClicked -> onPhotoClicked(event.photo)
            is AlbumDetailsScreenEvents.OnHidePhotoPreview -> onDismissPhotoDialog()
            is AlbumDetailsScreenEvents.UpdatePhotoSelection -> updatePhotoSelection(event.selectedIds)
            is AlbumDetailsScreenEvents.OnDeleteClicked -> deletePhotos(
                event.photosId,
                event.albumId
            )
        }
    }

    private fun getAlbumDetails(albumId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            getAlbumDetailsUseCase.invoke(albumId).collect { result ->
                when (result) {
                    is Resource.Loading -> viewModelState.update { it.copy(isLoading = true) }

                    is Resource.Success -> viewModelState.update {
                        it.copy(
                            album = result.data,
                            isLoading = false
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

    private fun onPhotoClicked(photo: BasePhoto) {
        viewModelState.update {
            it.copy(
                selectedPhoto = photo
            )
        }
    }

    private fun onDismissPhotoDialog() {
        viewModelState.update {
            it.copy(
                selectedPhoto = null
            )
        }
    }

    private fun updatePhotoSelection(selectedIds: Set<Long>) {
        viewModelState.update {
            it.copy(
                selectedIds = selectedIds
            )
        }
    }

    private fun deletePhotos(selectedIds: Set<Long>, albumId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            deletePhotosUseCase.invoke(selectedIds).collect { result ->
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
                        getAlbumDetails(albumId)
                    }
                }
            }
        }
    }
}