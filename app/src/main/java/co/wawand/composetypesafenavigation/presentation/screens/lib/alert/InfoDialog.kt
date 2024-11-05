package co.wawand.composetypesafenavigation.presentation.screens.lib.alert

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import co.wawand.composetypesafenavigation.R
import co.wawand.composetypesafenavigation.presentation.screens.lib.text.UiText

@Composable
fun InfoDialog(
    onClick: () -> Unit = {},
    onDismissClick: () -> Unit = {},
    message: UiText,
) {
    AlertDialog(
        onDismissRequest = onDismissClick,
        confirmButton = {
            TextButton(onClick = onClick) {
                Text(text = stringResource(id = R.string.info_dialog_confirm_button_text))
            }
        },
        icon = {
            Icon(Icons.Filled.Warning, null)
        },
        text = {
            Text(
                text = "${
                    stringResource(id = R.string.info_dialog_confirm_text_prefix)
                } ${message.asString()}"
            )
        }
    )
}

@Preview
@Composable
fun InfoDialogPreview() {
    InfoDialog(message = UiText.DynamicString(LoremIpsum(10).values.joinToString(" ")))
}