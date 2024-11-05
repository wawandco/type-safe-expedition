package co.wawand.composetypesafenavigation.presentation.navigation

import androidx.navigation.NavHostController

fun NavHostController.navigateWithClearBackStack(route: String) {
    navigate(route = route) {
        popUpTo(graph.startDestinationId) { inclusive = true }
    }
    graph.setStartDestination(route)
}
