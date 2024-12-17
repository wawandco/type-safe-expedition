package co.wawand.composetypesafenavigation.presentation.screens.welcome

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import co.wawand.composetypesafenavigation.R
import co.wawand.composetypesafenavigation.core.Constant
import co.wawand.composetypesafenavigation.presentation.navigation.NavigationEvent
import co.wawand.composetypesafenavigation.presentation.screens.lib.alert.InfoDialog
import co.wawand.composetypesafenavigation.presentation.screens.lib.layout.AppLayout
import co.wawand.composetypesafenavigation.presentation.screens.lib.loading.ResourceLoading
import co.wawand.composetypesafenavigation.presentation.screens.lib.text.UiText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WelcomeScreen(
    onNavigate: (NavigationEvent) -> Unit,
    onEvent: (WelcomeScreenEvents) -> Unit,
    state: WelcomeScreenState,
) {
    val keyboardManager = LocalSoftwareKeyboardController.current
    val scrollState = rememberScrollState()
    val context = LocalContext.current

    if (state.success) {
        LaunchedEffect(Unit) {
            onNavigate(NavigationEvent.NavigateToHome)
        }
    }

    AppLayout {
        if (state.isLoading) {
            ResourceLoading()
        }
        if (state.error.asString(context).isNotEmpty()) {
            InfoDialog(
                onClick = { onEvent(WelcomeScreenEvents.ClearError) },
                onDismissClick = { onEvent(WelcomeScreenEvents.ClearError) },
                message =
                if (state.error.asString(context) == Constant.GENERIC_ERROR) UiText.StringResource(
                    resId = R.string.generic_error
                )
                else state.error,
            )
        }
        Column(
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.background)
                .fillMaxSize()
                .verticalScroll(state = scrollState)
                .imePadding(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(id = R.drawable.android_color_logo),
                    contentDescription = null
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                OutlinedTextField(
                    placeholder = {
                        Text(text = stringResource(id = R.string.welcome_screen_user_name_input_label))
                    },
                    modifier = Modifier
                        .fillMaxWidth(),
                    value = state.name.asString(context),
                    onValueChange = {
                        onEvent(WelcomeScreenEvents.UpdateName(it))
                    },
                    keyboardActions = KeyboardActions(onDone = {
                        keyboardManager?.hide()
                        onEvent(WelcomeScreenEvents.PersistName(state.name.asString(context)))
                    }),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done
                    ),
                    trailingIcon = {
                        val userName = state.name.asString(context)
                        if (userName.isNotEmpty()) {
                            IconButton(onClick = {
                                onEvent(WelcomeScreenEvents.UpdateName(""))
                            }) {
                                Icon(imageVector = Icons.Filled.Clear, contentDescription = null)
                            }
                        }
                    }
                )

                Button(
                    onClick = {
                        keyboardManager?.hide()
                        val str = state.name.asString(context).ifEmpty {
                            UiText.StringResource(R.string.welcome_screen_guest_label)
                                .asString(context)
                        }

                        onEvent(WelcomeScreenEvents.PersistName(str))
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp),
                    shape = RectangleShape
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.login),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }

                Text(
                    modifier = Modifier.padding(12.dp),
                    text = stringResource(id = R.string.welcome_screen_text_info),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Preview
@Composable
fun WelcomeScreenPreviewWithName() {
    WelcomeScreen(
        onNavigate = {},
        onEvent = {},
        state = WelcomeScreenState(name = UiText.DynamicString("Joe"))
    )
}

@Preview
@Composable
fun WelcomeScreenPreviewWithoutName() {
    WelcomeScreen(
        onNavigate = {},
        onEvent = {},
        state = WelcomeScreenState(name = UiText.DynamicString(""))
    )
}

@Preview
@Composable
fun WelcomeScreenPreviewLoading() {
    WelcomeScreen(
        onNavigate = {},
        onEvent = {},
        state = WelcomeScreenState(
            name = UiText.DynamicString("Joe"),
            isLoading = true
        )
    )
}

@Preview
@Composable
fun WelcomeScreenPreviewError() {
    WelcomeScreen(
        onNavigate = {},
        onEvent = {},
        state = WelcomeScreenState(
            name = UiText.DynamicString("Joe"),
            error = UiText.DynamicString("Cannot persist the user name")
        )
    )
}

@Preview
@Composable
fun WelcomeScreenPreviewGenericError() {
    WelcomeScreen(
        onNavigate = {},
        onEvent = {},
        state = WelcomeScreenState(
            name = UiText.DynamicString("Joe"),
            error = UiText.DynamicString(Constant.GENERIC_ERROR)
        )
    )
}