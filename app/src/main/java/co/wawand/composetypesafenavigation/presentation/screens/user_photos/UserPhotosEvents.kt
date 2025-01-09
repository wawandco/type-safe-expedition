package co.wawand.composetypesafenavigation.presentation.screens.user_photos

import co.wawand.composetypesafenavigation.domain.model.BasePhoto
import co.wawand.composetypesafenavigation.presentation.screens.lib.text.UiText

sealed class UserPhotosEvents {
    data class SetCameraPermission(val hasCameraPermission: Boolean) : UserPhotosEvents()
    data class OnDeleteClicked(val photosId: Set<Long>) : UserPhotosEvents()
    data object OnGoToCameraClicked : UserPhotosEvents()
    data class HandleCameraPermissionError(val message: UiText) : UserPhotosEvents()
    data object ClearError : UserPhotosEvents()
    data class OnPhotoClicked(val photo: BasePhoto) : UserPhotosEvents()
    data object OnHidePhotoPreview : UserPhotosEvents()
    data class UpdatePhotoSelection(val selectedIds: Set<Long>) : UserPhotosEvents()
}