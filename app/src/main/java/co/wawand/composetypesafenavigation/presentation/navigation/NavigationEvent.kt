package co.wawand.composetypesafenavigation.presentation.navigation

import androidx.navigation.NavHostController

sealed class NavigationEvent {
    data object OnNavigateUp : NavigationEvent()
    data object NavigateToHome : NavigationEvent()
    data class OnNavigateToPostDetails(val postId: Long) : NavigationEvent()
    data class OnNavigateToAlbumDetails(val albumId: Long) : NavigationEvent()
}

fun handleNavigationEvent(navController: NavHostController, event: NavigationEvent) {
    when (event) {

        is NavigationEvent.OnNavigateUp -> navController.navigateUp()

        is NavigationEvent.NavigateToHome -> navController.navigate(AppDestinations.Home) {
            popUpTo(navController.graph.startDestinationId) { inclusive = true }
        }

        is NavigationEvent.OnNavigateToPostDetails -> navController.navigate(
            AppDestinations.PostDetail(event.postId)
        )

        is NavigationEvent.OnNavigateToAlbumDetails -> navController.navigate(
            AppDestinations.AlbumDetails(event.albumId)
        )
    }
}