package co.wawand.composetypesafenavigation.presentation.screens.lib.input

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction

@Composable
fun OutlinedField(
    modifier: Modifier,
    labelText: String,
    value: String,
    onValueChange: (String) -> Unit,
    trailingIcon: @Composable (() -> Unit) = {},
    enabled: Boolean = true,
) {
    val keyboardManager = LocalSoftwareKeyboardController.current
    OutlinedTextField(
        modifier = modifier,
        label = {
            Text(text = labelText)
        },
        value = value, onValueChange = onValueChange,
        keyboardActions = KeyboardActions(
            onDone = {
                keyboardManager?.hide()
            }
        ),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done
        ),
        trailingIcon = trailingIcon,
        enabled = enabled,
    )
}