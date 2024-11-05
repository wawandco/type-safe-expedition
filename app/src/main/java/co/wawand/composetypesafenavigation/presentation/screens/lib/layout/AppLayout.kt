package co.wawand.composetypesafenavigation.presentation.screens.lib.layout

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import co.wawand.composetypesafenavigation.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppLayout(
    layoutModifier: Modifier = Modifier,
    topAppBarModifier: Modifier = Modifier,
    navigationConfiguration: NavigationConfiguration = NavigationConfiguration(),
    @StringRes titleResId: Int = R.string.app_name,
    topAppBarScrollBehavior: TopAppBarScrollBehavior? = null,
    floatingActionButton: @Composable () -> Unit = {},
    floatingActionButtonPosition: FabPosition = FabPosition.End,
    bottomBarActions: @Composable () -> Unit = {},
    action: (() -> Unit)? = null,
    iconVectorAction: ImageVector = Icons.Filled.Settings,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    content: @Composable ColumnScope.() -> Unit,
) {
    Scaffold(
        modifier = layoutModifier,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            DefaultTopBar(
                modifier = topAppBarModifier,
                navigationIcon = {
                    IconButton(onClick = navigationConfiguration.onClick) {
                        Icon(
                            imageVector = navigationConfiguration.icon,
                            contentDescription = navigationConfiguration.contentDescription,
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
                action = action,
                iconVector = iconVectorAction,
                titleResId = titleResId,
                scrollBehaviour = topAppBarScrollBehavior
            )
        },
        floatingActionButton = floatingActionButton,
        floatingActionButtonPosition = floatingActionButtonPosition,
        bottomBar = {
            bottomBarActions()
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            content()
        }
    }
}

@Composable
private fun DefaultTopAppBarNavigationIcon(onClick: () -> Unit) {
    IconButton(onClick = onClick) {
        Icon(
            imageVector = Icons.Default.Menu,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DefaultTopBar(
    modifier: Modifier,
    navigationIcon: @Composable () -> Unit,
    action: (() -> Unit)? = null,
    iconVector: ImageVector,
    @StringRes titleResId: Int,
    scrollBehaviour: TopAppBarScrollBehavior?
) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        navigationIcon = navigationIcon,
        title = {
            Text(
                text = stringResource(titleResId),
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        actions = {
            if (action != null) {
                IconButton(onClick = action) {
                    Icon(
                        imageVector = iconVector,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        },
        scrollBehavior = scrollBehaviour
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun TopAppBarPreview() {
    DefaultTopBar(
        modifier = Modifier,
        navigationIcon = { DefaultTopAppBarNavigationIcon {} },
        action = {},
        iconVector = Icons.Filled.Settings,
        titleResId = R.string.home_screen_unknown_label,
        scrollBehaviour = null
    )
}