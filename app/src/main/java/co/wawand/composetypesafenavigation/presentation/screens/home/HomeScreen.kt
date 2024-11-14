package co.wawand.composetypesafenavigation.presentation.screens.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.twotone.Build
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import co.wawand.composetypesafenavigation.R
import co.wawand.composetypesafenavigation.presentation.navigation.NavigationEvent
import co.wawand.composetypesafenavigation.presentation.screens.lib.layout.AppLayout
import co.wawand.composetypesafenavigation.presentation.screens.lib.loading.ResourceLoading
import co.wawand.composetypesafenavigation.presentation.utils.mockdata.generateStaticAlbums
import co.wawand.composetypesafenavigation.presentation.utils.mockdata.generateStaticAuthors
import co.wawand.composetypesafenavigation.presentation.utils.mockdata.generateStaticPosts

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigate: (NavigationEvent) -> Unit,
    onEvent: (HomeScreenEvents) -> Unit,
    state: HomeScreenState
) {
    AppLayout(
        titleResId = R.string.home_screen_main_label,
        action = {},
        iconVectorAction = Icons.TwoTone.Build,
        floatingActionButton = {
            FloatingActionButton(onClick = {
                when (state.currentSection) {
                    HomeSection.POSTS -> onEvent(HomeScreenEvents.OnRefreshPosts)
                    HomeSection.ALBUMS -> onEvent(HomeScreenEvents.OnRefreshAlbums)
                    HomeSection.AUTHORS -> onEvent(HomeScreenEvents.OnRefreshAuthors)
                }
            }) {
                Icon(
                    imageVector = Icons.Filled.Refresh,
                    contentDescription = stringResource(id = R.string.home_screen_refresh_label)
                )
            }
        },
        bottomBarActions = {
            HomeBottomBar(selectedSection = state.currentSection, onSectionSelected = {
                onEvent(HomeScreenEvents.OnHomeSectionChanged(it))
            })
        }
    ) {
        if (state.isLoading) {
            ResourceLoading()
        } else {
            when (state.currentSection) {
                HomeSection.POSTS -> PostList(
                    postList = state.posts ?: emptyList(),
                    onPostClick = { postId ->
                        onNavigate(NavigationEvent.OnNavigateToPostDetails(postId))
                    }
                )

                HomeSection.ALBUMS -> AlbumList(
                    albumList = state.albums ?: emptyList(),
                    onAlbumClick = { albumId ->
                        onNavigate(NavigationEvent.OnNavigateToAlbumDetails(albumId))
                    }
                )

                HomeSection.AUTHORS -> AuthorList(state.authors ?: emptyList())
            }
        }

    }
}

@Preview
@Composable
fun HomeScreenPreviewAlbums() {
    HomeScreen(
        onNavigate = {},
        onEvent = {},
        state = HomeScreenState(
            currentSection = HomeSection.ALBUMS,
            albums = generateStaticAlbums()
        )
    )
}

@Preview
@Composable
fun HomeScreenPreviewPosts() {
    HomeScreen(
        onNavigate = {},
        onEvent = {},
        state = HomeScreenState(posts = generateStaticPosts())
    )
}

@Preview
@Composable
fun HomeScreenPreviewEmptyPostList() {
    HomeScreen(
        onNavigate = {},
        onEvent = {},
        state = HomeScreenState()
    )
}

@Preview
@Composable
fun HomeScreenPreviewAuthors() {
    HomeScreen(
        onNavigate = {},
        onEvent = {},
        state = HomeScreenState(
            currentSection = HomeSection.AUTHORS,
            authors = generateStaticAuthors()
        ),
    )
}

