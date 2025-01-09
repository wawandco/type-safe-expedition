package co.wawand.composetypesafenavigation.domain.model

import androidx.compose.runtime.Immutable

@Immutable
data class Album(
    val id: Long,
    val title: String,
    val owner: Author?,
    val photos: Int,
)

@Immutable
data class AlbumWithPhotos(
    val id: Long,
    val title: String,
    val owner: Author?,
    val photos: List<BasePhoto>,
)