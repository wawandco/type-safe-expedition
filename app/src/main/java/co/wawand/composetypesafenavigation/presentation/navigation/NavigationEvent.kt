package co.wawand.composetypesafenavigation.presentation.navigation

import androidx.navigation.NavHostController

sealed class NavigationEvent {
    data object OnNavigateUp : NavigationEvent()
    data object OnNavigateToHome : NavigationEvent()
    data class OnNavigateToPostDetails(val postId: Long) : NavigationEvent()
    data class OnNavigateToAlbumDetails(val albumId: Long) : NavigationEvent()
    data object OnNavigateToUserPhotos : NavigationEvent()
    data object OnNavigateToAddPhoto : NavigationEvent()
    data class OnNavigateToPhotoPreview(val photoUri: String) : NavigationEvent()
}

fun handleNavigationEvent(navController: NavHostController, event: NavigationEvent) {
    when (event) {

        is NavigationEvent.OnNavigateUp -> navController.navigateUp()

        is NavigationEvent.OnNavigateToHome -> navController.navigate(AppDestinations.Home) {
            popUpTo(navController.graph.startDestinationId) { inclusive = true }
        }

        is NavigationEvent.OnNavigateToPostDetails -> navController.navigate(
            AppDestinations.PostDetail(event.postId)
        )

        is NavigationEvent.OnNavigateToAlbumDetails -> navController.navigate(
            AppDestinations.AlbumDetails(event.albumId)
        )

        is NavigationEvent.OnNavigateToUserPhotos -> navController.navigate(AppDestinations.UserPhotos)

        is NavigationEvent.OnNavigateToAddPhoto -> navController.navigate(AppDestinations.AddPhoto)

        is NavigationEvent.OnNavigateToPhotoPreview -> navController.navigate(
            AppDestinations.PhotoPreview(event.photoUri)
        )

    }
}