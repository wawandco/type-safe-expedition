package co.wawand.composetypesafenavigation.presentation.screens.home

import co.wawand.composetypesafenavigation.domain.model.Album
import co.wawand.composetypesafenavigation.domain.model.Author
import co.wawand.composetypesafenavigation.domain.model.Post
import co.wawand.composetypesafenavigation.presentation.screens.lib.text.UiText

data class HomeScreenState(
    val currentSection: HomeSection = HomeSection.POSTS,
    val isLoading: Boolean = false,
    val error: UiText? = null,
    val posts: List<Post>? = null,
    val albums: List<Album>? = null,
    val authors: List<Author>? = null,
)