package co.wawand.composetypesafenavigation.presentation.screens.welcome

sealed class WelcomeScreenEvents {
    data class UpdateName(val q: String) : WelcomeScreenEvents()
    data class PersistName(val q: String) : WelcomeScreenEvents()
    data object ClearError : WelcomeScreenEvents()
}