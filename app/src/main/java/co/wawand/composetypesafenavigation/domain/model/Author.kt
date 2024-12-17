package co.wawand.composetypesafenavigation.domain.model

import androidx.compose.runtime.Immutable

@Immutable
data class Author(
    val id: Long = 0,
    val name: String,
    val username: String,
    val email: String,
    val albums: Int = 0,
    val posts: Int = 0,
)