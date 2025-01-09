package co.wawand.composetypesafenavigation.presentation.screens.details.album

import co.wawand.composetypesafenavigation.domain.model.BasePhoto

sealed class AlbumDetailsScreenEvents {
    data class LoadAlbumDetails(val albumId: Long) : AlbumDetailsScreenEvents()
    data class OnPhotoClicked(val photo: BasePhoto) : AlbumDetailsScreenEvents()
    data object OnHidePhotoPreview : AlbumDetailsScreenEvents()
    data class UpdatePhotoSelection(val selectedIds: Set<Long>) : AlbumDetailsScreenEvents()
    data class OnDeleteClicked(val photosId: Set<Long>, val albumId: Long) : AlbumDetailsScreenEvents()
}