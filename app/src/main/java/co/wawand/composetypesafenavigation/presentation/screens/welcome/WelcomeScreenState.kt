package co.wawand.composetypesafenavigation.presentation.screens.welcome

import co.wawand.composetypesafenavigation.presentation.screens.lib.text.UiText

data class WelcomeScreenState(
    val name: UiText = UiText.DynamicString(""),
    val isLoading: Boolean = false,
    val success: Boolean = false,
    val error: UiText = UiText.DynamicString(""),
)