package co.wawand.composetypesafenavigation.domain.model

import androidx.compose.runtime.Immutable

@Immutable
data class Album(
    val id: Long,
    val title: String,
    val owner: Author?,
    val photos: Int,
)