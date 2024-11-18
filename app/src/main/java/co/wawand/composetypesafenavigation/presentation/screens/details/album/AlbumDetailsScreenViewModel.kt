package co.wawand.composetypesafenavigation.presentation.screens.details.album

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.wawand.composetypesafenavigation.core.Constant.GENERIC_ERROR
import co.wawand.composetypesafenavigation.core.util.Resource
import co.wawand.composetypesafenavigation.domain.model.Photo
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
    private val getAlbumDetailsUseCase: GetAlbumDetailsUseCase
) : ViewModel() {

    private val viewModelState = MutableStateFlow(AlbumDetailsScreenState())
    val uiState = viewModelState.asStateFlow()

    fun onEvent(event: AlbumDetailsScreenEvents) {
        when (event) {
            is AlbumDetailsScreenEvents.LoadAlbumDetails -> getAlbumDetails(event.albumId)
            is AlbumDetailsScreenEvents.OnPhotoClicked -> onPhotoClicked(event.photo)
            is AlbumDetailsScreenEvents.OnDismissPhotoDialog -> onDismissPhotoDialog()
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

    private fun onPhotoClicked(photo: Photo) {
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
}