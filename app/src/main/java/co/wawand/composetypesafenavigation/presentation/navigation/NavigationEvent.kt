package co.wawand.composetypesafenavigation.presentation.navigation

import androidx.navigation.NavController

sealed class NavigationEvent {
    data object OnNavigateUp : NavigationEvent()
    data object NavigateToHome : NavigationEvent()
    data class OnNavigateToPostDetails(val postId: Long) : NavigationEvent()
}

fun handleNavigationEvent(navController: NavController, event: NavigationEvent) {
    when (event) {

        is NavigationEvent.OnNavigateUp -> navController::navigateUp

        is NavigationEvent.NavigateToHome -> {
            navController.popBackStack(route = AppDestinations.Home, inclusive = true)
        }

        is NavigationEvent.OnNavigateToPostDetails -> {
            navController.navigate(AppDestinations.PostDetail(event.postId))
        }
    }
}