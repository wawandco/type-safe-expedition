package co.wawand.composetypesafenavigation.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import co.wawand.composetypesafenavigation.presentation.navigation.AppNavigation
import co.wawand.composetypesafenavigation.presentation.navigation.AppState
import co.wawand.composetypesafenavigation.presentation.navigation.MainViewModel
import co.wawand.composetypesafenavigation.presentation.theme.ComposeTypeSafeNavigationTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        installSplashScreen()
        setContent {
            val appState by mainViewModel.isLoggedIn.collectAsState(initial = AppState.LOADING)
            ComposeTypeSafeNavigationTheme {
                AppNavigation(navController = rememberNavController(), appState = appState)
            }
        }
    }
}