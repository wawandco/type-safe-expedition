package co.wawand.composetypesafenavigation.presentation.screens.details.post

sealed class PostDetailsScreenEvents {
    data class LoadPostDetails(val postId: Long) : PostDetailsScreenEvents()
    data class LoadRelatedPosts(val userId: Long) : PostDetailsScreenEvents()
}