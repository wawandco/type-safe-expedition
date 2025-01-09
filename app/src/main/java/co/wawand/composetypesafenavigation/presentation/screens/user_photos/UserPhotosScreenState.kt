package co.wawand.composetypesafenavigation.presentation.screens.user_photos

import co.wawand.composetypesafenavigation.domain.model.BasePhoto
import co.wawand.composetypesafenavigation.presentation.screens.lib.text.UiText

data class UserPhotosScreenState(
    val isLoading: Boolean = false,
    val hasCameraPermission: Boolean = false,
    val showCamera: Boolean = true,
    val photos: List<BasePhoto> = emptyList(),
    val error: UiText = UiText.DynamicString(""),
    val selectedPhoto: BasePhoto? = null,
    val selectedIds: Set<Long> = emptySet()
)