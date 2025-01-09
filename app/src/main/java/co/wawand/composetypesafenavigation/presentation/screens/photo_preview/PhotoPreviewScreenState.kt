package co.wawand.composetypesafenavigation.presentation.screens.photo_preview

import co.wawand.composetypesafenavigation.presentation.screens.lib.text.UiText

sealed class PhotoPreviewNavigation {
    data object None : PhotoPreviewNavigation()
    data object NavigateToAddPhoto : PhotoPreviewNavigation()
    data object NavigateToUserPhotos : PhotoPreviewNavigation()
}

data class PhotoPreviewScreenState(
    val isLoading: Boolean = false,
    val error: UiText = UiText.DynamicString(""),
    val photoUri: String? = null,
    val isPhotoSaved: Boolean = false,
    val navigation: PhotoPreviewNavigation = PhotoPreviewNavigation.None
)