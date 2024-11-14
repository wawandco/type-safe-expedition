package co.wawand.composetypesafenavigation.presentation.navigation

import kotlinx.serialization.Serializable

sealed class AppDestinations {
    @Serializable
    data object Welcome : AppDestinations()

    @Serializable
    data object Home : AppDestinations()

    @Serializable
    data object Loading : AppDestinations()

    @Serializable
    data class PostDetail(val postId: Long) : AppDestinations()

    @Serializable
    data class AlbumDetails(val albumId: Long) : AppDestinations()
}