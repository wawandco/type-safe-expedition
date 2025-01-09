package co.wawand.composetypesafenavigation.presentation.screens.details.album

import co.wawand.composetypesafenavigation.domain.model.AlbumWithPhotos
import co.wawand.composetypesafenavigation.domain.model.BasePhoto
import co.wawand.composetypesafenavigation.presentation.screens.lib.text.UiText

data class AlbumDetailsScreenState(
    val isLoading: Boolean = false,
    val album: AlbumWithPhotos? = null,
    val error: UiText = UiText.DynamicString(""),
    val selectedPhoto: BasePhoto? = null,
    val selectedIds: Set<Long> = emptySet()
)