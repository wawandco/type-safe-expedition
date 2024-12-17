package co.wawand.composetypesafenavigation.domain.model

import androidx.compose.runtime.Immutable

@Immutable
data class Post(
    val id: Long = 0,
    val title: String,
    val body: String,
    val author: Author? = null,
)