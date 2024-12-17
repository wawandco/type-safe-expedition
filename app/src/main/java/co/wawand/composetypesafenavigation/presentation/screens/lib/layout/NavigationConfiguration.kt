package co.wawand.composetypesafenavigation.presentation.screens.lib.layout

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.ui.graphics.vector.ImageVector

data class NavigationConfiguration(
    val onClick: () -> Unit = {},
    val icon: ImageVector = Icons.Default.Menu,
    val contentDescription: String? = null
)