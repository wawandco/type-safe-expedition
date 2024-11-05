package co.wawand.composetypesafenavigation.presentation.navigation

import androidx.navigation.NavController
import co.wawand.composetypesafenavigation.presentation.navigation.NavDestinations.HOME_ROUTE

sealed class NavigationEvent {
    data object OnNavigateUp : NavigationEvent()
    data object NavigateToHome : NavigationEvent()
    data class OnNavigateToPostDetails(val postId: Long) : NavigationEvent()
}

fun handleNavigationEvent(navController: NavController, event: NavigationEvent) {
    when (event) {

        is NavigationEvent.OnNavigateUp -> navController::navigateUp

        is NavigationEvent.NavigateToHome -> {
            navController.navigate(HOME_ROUTE) {
                popUpTo(HOME_ROUTE) { inclusive = true }
            }
        }

        is NavigationEvent.OnNavigateToPostDetails -> {
            navController.navigate(NavDestinations.postDetailsRoute(event.postId))
        }
    }
}