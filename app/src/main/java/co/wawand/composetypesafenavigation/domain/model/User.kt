package co.wawand.composetypesafenavigation.domain.model

import androidx.compose.runtime.Immutable

@Immutable
data class User(
    val id: Long = 0,
    val name: String,
)