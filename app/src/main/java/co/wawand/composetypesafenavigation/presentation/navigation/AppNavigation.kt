package co.wawand.composetypesafenavigation.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import co.wawand.composetypesafenavigation.presentation.screens.add_photo.AddPhotoScreen
import co.wawand.composetypesafenavigation.presentation.screens.add_photo.AddPhotoScreenViewModel
import co.wawand.composetypesafenavigation.presentation.screens.details.album.AlbumDetailsScreen
import co.wawand.composetypesafenavigation.presentation.screens.details.album.AlbumDetailsScreenViewModel
import co.wawand.composetypesafenavigation.presentation.screens.details.post.PostDetailsScreen
import co.wawand.composetypesafenavigation.presentation.screens.details.post.PostDetailsScreenViewModel
import co.wawand.composetypesafenavigation.presentation.screens.home.HomeScreen
import co.wawand.composetypesafenavigation.presentation.screens.home.HomeScreenViewModel
import co.wawand.composetypesafenavigation.presentation.screens.lib.loading.ScreenLoading
import co.wawand.composetypesafenavigation.presentation.screens.photo_preview.PhotoPreviewScreen
import co.wawand.composetypesafenavigation.presentation.screens.photo_preview.PhotoPreviewScreenViewModel
import co.wawand.composetypesafenavigation.presentation.screens.user_photos.UserPhotosScreen
import co.wawand.composetypesafenavigation.presentation.screens.user_photos.UserPhotosScreenViewModel
import co.wawand.composetypesafenavigation.presentation.screens.welcome.WelcomeScreen
import co.wawand.composetypesafenavigation.presentation.screens.welcome.WelcomeScreenViewModel

@Composable
fun AppNavigation(navController: NavHostController, appState: AppState) {
    val startDestination = resolveStartDestination(appState)

    NavHost(navController = navController, startDestination = startDestination) {

        composable<AppDestinations.Loading> {
            ScreenLoading()
        }

        composable<AppDestinations.Welcome> {
            val welcomeScreenViewModel = hiltViewModel<WelcomeScreenViewModel>()
            WelcomeScreen(
                onNavigate = { event -> handleNavigationEvent(navController, event) },
                onEvent = { event -> welcomeScreenViewModel.onEvent(event) },
                state = welcomeScreenViewModel.state
            )
        }

        composable<AppDestinations.Home> {
            val homeScreenViewModel = hiltViewModel<HomeScreenViewModel>()
            val state by homeScreenViewModel.uiState.collectAsState()
            HomeScreen(
                onNavigate = { event -> handleNavigationEvent(navController, event) },
                onEvent = { event -> homeScreenViewModel.onEvent(event) },
                state = state
            )
        }

        composable<AppDestinations.PostDetail> { navBackStackEntry ->
            val postDetail = navBackStackEntry.toRoute<AppDestinations.PostDetail>()
            val postDetailsScreenViewModel = hiltViewModel<PostDetailsScreenViewModel>()
            val state by postDetailsScreenViewModel.uiState.collectAsState()
            PostDetailsScreen(
                postId = postDetail.postId,
                onNavigate = { event -> handleNavigationEvent(navController, event) },
                onEvent = { event -> postDetailsScreenViewModel.onEvent(event) },
                state = state
            )
        }

        composable<AppDestinations.AlbumDetails> { navBackStackEntry ->
            val albumDetail = navBackStackEntry.toRoute<AppDestinations.AlbumDetails>()
            val albumDetailsScreenViewModel = hiltViewModel<AlbumDetailsScreenViewModel>()
            val state by albumDetailsScreenViewModel.uiState.collectAsState()
            AlbumDetailsScreen(
                albumId = albumDetail.albumId,
                onNavigate = { event -> handleNavigationEvent(navController, event) },
                onEvent = { event -> albumDetailsScreenViewModel.onEvent(event) },
                state = state
            )
        }

        composable<AppDestinations.UserPhotos> {
            val userPhotosScreenViewModel = hiltViewModel<UserPhotosScreenViewModel>()
            val state by userPhotosScreenViewModel.uiState.collectAsState()
            UserPhotosScreen(
                onNavigate = { event -> handleNavigationEvent(navController, event) },
                onEvent = { event -> userPhotosScreenViewModel.onEvent(event) },
                state = state
            )
        }

        composable<AppDestinations.AddPhoto> {
            val addPhotoScreenViewModel = hiltViewModel<AddPhotoScreenViewModel>()
            val state by addPhotoScreenViewModel.uiState.collectAsState()
            AddPhotoScreen(
                onNavigate = { event -> handleNavigationEvent(navController, event) },
                onEvent = { event -> addPhotoScreenViewModel.onEvent(event) },
                state = state
            )
        }

        composable<AppDestinations.PhotoPreview> { navBackStackEntry ->
            val photoPreview = navBackStackEntry.toRoute<AppDestinations.PhotoPreview>()
            val photoPreviewScreenViewModel = hiltViewModel<PhotoPreviewScreenViewModel>()
            val state by photoPreviewScreenViewModel.uiState.collectAsState()
            PhotoPreviewScreen(
                uriString = photoPreview.photoUri,
                onNavigate = { event -> handleNavigationEvent(navController, event) },
                onEvent = { event -> photoPreviewScreenViewModel.onEvent(event) },
                state = state
            )
        }

    }
}

private fun resolveStartDestination(appState: AppState): AppDestinations {
    return when (appState) {
        AppState.LOADING -> AppDestinations.Loading
        AppState.IDLE -> AppDestinations.Welcome
        AppState.LOGGED_IN -> AppDestinations.Home
    }
}

enum class AppState {
    LOADING,
    IDLE,

    //ERROR,
    LOGGED_IN
}