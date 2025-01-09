package co.wawand.composetypesafenavigation.presentation.screens.photo_preview

import java.io.File

sealed class PhotoPreviewScreenEvents {
    data class LoadPhotoUri(val uriString: String) : PhotoPreviewScreenEvents()
    data object OnPersistUserPhoto : PhotoPreviewScreenEvents()
    data object ClearError : PhotoPreviewScreenEvents()
    data class RemovePreviewAndRedirect(
        val photoFilePreview: File,
        val navigateTo: PhotoPreviewNavigation
    ) :
        PhotoPreviewScreenEvents()
}