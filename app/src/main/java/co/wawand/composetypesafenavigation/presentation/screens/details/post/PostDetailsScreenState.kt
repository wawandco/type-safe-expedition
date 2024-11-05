package co.wawand.composetypesafenavigation.presentation.screens.details.post

import co.wawand.composetypesafenavigation.domain.model.Post
import co.wawand.composetypesafenavigation.presentation.screens.lib.text.UiText

data class PostDetailsScreenState(
    val isLoadingPost: Boolean = false,
    val isLoadingRelatedPosts: Boolean = false,
    val post: Post? = null,
    val relatedPosts: List<Post> = emptyList(),
    val error: UiText = UiText.DynamicString(""),
) {
    val isLoading: Boolean
        get() = isLoadingPost || isLoadingRelatedPosts
}