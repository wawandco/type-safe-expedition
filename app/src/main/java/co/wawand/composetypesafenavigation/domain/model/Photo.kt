package co.wawand.composetypesafenavigation.domain.model

import androidx.compose.runtime.Immutable

@Immutable
data class Photo(
    val id: Long,
    val title: String,
    val thumbnailUrl: String,
    val url: String,
    val album: String,
)
