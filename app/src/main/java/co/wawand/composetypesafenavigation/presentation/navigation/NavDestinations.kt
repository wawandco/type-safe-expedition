package co.wawand.composetypesafenavigation.presentation.navigation

import co.wawand.composetypesafenavigation.presentation.navigation.NavArguments.POST_ID

object NavDestinations {
    const val WELCOME_ROUTE = "welcome"
    const val HOME_ROUTE = "home"
    const val LOADING_ROUTE = "loading"


    private const val POST_DETAILS = "post_details"
    const val POST_DETAILS_ROUTE = "$POST_DETAILS/{$POST_ID}"

    // Helper function to create the route with parameter
    fun postDetailsRoute(postId: Long) = "$POST_DETAILS/$postId"
}

object NavArguments {
    const val POST_ID = "postId"
}