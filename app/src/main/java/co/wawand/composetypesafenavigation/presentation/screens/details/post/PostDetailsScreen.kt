package co.wawand.composetypesafenavigation.presentation.screens.details.post

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import co.wawand.composetypesafenavigation.R
import co.wawand.composetypesafenavigation.presentation.navigation.NavigationEvent
import co.wawand.composetypesafenavigation.presentation.screens.lib.layout.AppLayout
import co.wawand.composetypesafenavigation.presentation.screens.lib.layout.NavigationConfiguration
import co.wawand.composetypesafenavigation.presentation.screens.lib.loading.ResourceLoading
import co.wawand.composetypesafenavigation.presentation.utils.mockdata.generateStaticPosts

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostDetailsScreen(
    postId: Long,
    onNavigate: (NavigationEvent) -> Unit,
    onEvent: (PostDetailsScreenEvents) -> Unit,
    state: PostDetailsScreenState,
) {
    LaunchedEffect(key1 = postId) {
        onEvent(PostDetailsScreenEvents.LoadPostDetails(postId))
    }

    LaunchedEffect(key1 = state.post?.author?.id) {
        state.post?.author?.id?.let { userId ->
            onEvent(PostDetailsScreenEvents.LoadRelatedPosts(userId))
        }
    }

    AppLayout(
        titleResId = R.string.details_post_screen_main_label,
        navigationConfiguration = NavigationConfiguration(
            onClick = { onNavigate(NavigationEvent.NavigateToHome) },
            icon = Icons.AutoMirrored.Filled.ArrowBack
        )
    ) {

        if (state.isLoading) {
            ResourceLoading()
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.Transparent)
            ) {
                PostDetails(post = state.post, relatedPosts = state.relatedPosts)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PostDetailsScreenPreviewNoRelatedPosts() {
    PostDetailsScreen(
        postId = 1L,
        onNavigate = {},
        onEvent = {},
        state = PostDetailsScreenState(
            post = generateStaticPosts().first(),
            relatedPosts = emptyList()
        )
    )
}

@Preview(showBackground = true)
@Composable
fun PostDetailsScreenPreview() {
    PostDetailsScreen(
        postId = 1L,
        onNavigate = {},
        onEvent = {},
        state = PostDetailsScreenState(
            post = generateStaticPosts().first(),
            relatedPosts = generateStaticPosts()
        )
    )
}