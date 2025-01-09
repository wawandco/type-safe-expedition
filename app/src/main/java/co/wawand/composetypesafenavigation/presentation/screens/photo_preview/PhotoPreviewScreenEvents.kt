package co.wawand.composetypesafenavigation.presentation.screens.photo_preview

sealed class PhotoPreviewScreenEvents {
    data class LoadPhotoUri(val uriString: String) : PhotoPreviewScreenEvents()
    data object OnPersistUserPhoto : PhotoPreviewScreenEvents()
    data object ClearError : PhotoPreviewScreenEvents()
}