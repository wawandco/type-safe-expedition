package co.wawand.composetypesafenavigation.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import co.wawand.composetypesafenavigation.presentation.screens.details.post.PostDetailsScreen
import co.wawand.composetypesafenavigation.presentation.screens.details.post.PostDetailsScreenViewModel
import co.wawand.composetypesafenavigation.presentation.screens.home.HomeScreen
import co.wawand.composetypesafenavigation.presentation.screens.home.HomeScreenViewModel
import co.wawand.composetypesafenavigation.presentation.screens.lib.loading.ScreenLoading
import co.wawand.composetypesafenavigation.presentation.screens.welcome.WelcomeScreen
import co.wawand.composetypesafenavigation.presentation.screens.welcome.WelcomeScreenViewModel

@Composable
fun AppNavigation(navController: NavHostController, appState: AppState) {
    val startDestination = resolveStartDestination(appState)

    NavHost(navController = navController, startDestination = startDestination) {

        composable(route = NavDestinations.LOADING_ROUTE) {
            ScreenLoading()
        }

        composable(route = NavDestinations.WELCOME_ROUTE) {
            val welcomeScreenViewModel = hiltViewModel<WelcomeScreenViewModel>()
            WelcomeScreen(
                onNavigate = { event -> handleNavigationEvent(navController, event) },
                onEvent = { event -> welcomeScreenViewModel.onEvent(event) },
                state = welcomeScreenViewModel.state
            )
        }

        composable(route = NavDestinations.HOME_ROUTE) {
            val homeScreenViewModel = hiltViewModel<HomeScreenViewModel>()
            val state by homeScreenViewModel.uiState.collectAsState()
            HomeScreen(
                onNavigate = { event -> handleNavigationEvent(navController, event) },
                onEvent = { event -> homeScreenViewModel.onEvent(event) },
                state = state
            )
        }

        composable(
            route = NavDestinations.POST_DETAILS_ROUTE,
            arguments = listOf(
                navArgument(NavArguments.POST_ID) {
                    type = NavType.LongType
                }
            )
        ) { navBackStackEntry ->
            val postId = navBackStackEntry.arguments?.getLong(NavArguments.POST_ID) ?: 0L
            val postDetailsScreenViewModel = hiltViewModel<PostDetailsScreenViewModel>()
            val state by postDetailsScreenViewModel.uiState.collectAsState()
            PostDetailsScreen(
                postId = postId,
                onNavigate = { event -> handleNavigationEvent(navController, event) },
                onEvent = { event -> postDetailsScreenViewModel.onEvent(event) },
                state = state
            )
        }
    }
}

private fun resolveStartDestination(appState: AppState): String {
    return when (appState) {
        AppState.LOADING -> NavDestinations.LOADING_ROUTE
        AppState.IDLE -> NavDestinations.WELCOME_ROUTE
        AppState.LOGGED_IN -> NavDestinations.HOME_ROUTE
    }
}

enum class AppState {
    LOADING,
    IDLE,

    //ERROR,
    LOGGED_IN
}