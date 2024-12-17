package co.wawand.composetypesafenavigation.presentation.screens.details.album

import co.wawand.composetypesafenavigation.domain.model.Photo

sealed class AlbumDetailsScreenEvents {
    data class LoadAlbumDetails(val albumId: Long) : AlbumDetailsScreenEvents()
    data class OnPhotoClicked(val photo: Photo) : AlbumDetailsScreenEvents()
    data object OnDismissPhotoDialog : AlbumDetailsScreenEvents()
}