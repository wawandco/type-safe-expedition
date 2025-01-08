package co.wawand.composetypesafenavigation.presentation.screens.lib.camera

import android.view.ViewGroup
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import co.wawand.composetypesafenavigation.R

@Composable
fun CameraView(
    previewView: PreviewView,
    flashEnabled: Boolean,
    onFlashIconClick: () -> Unit = {},
    goBack: () -> Unit = {},
    applyActionButton: (@Composable BoxScope.() -> Unit)? = null,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .background(Color.Black)
    ) {
        AndroidView(
            factory = { previewView },
            modifier = Modifier.fillMaxSize(),
            onRelease = {}
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .align(Alignment.TopCenter)
                .background(Color.Black.copy(alpha = 0.7f))
        )

        // Grid Layer - positioned between top and bottom bars
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
                .padding(top = 56.dp)
                .height(with(LocalDensity.current) {
                    LocalConfiguration.current.screenHeightDp.dp - 56.dp - 100.dp
                })
        ) {
            CameraGrid()
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .align(Alignment.BottomCenter)
                .background(Color.Black.copy(alpha = 0.7f))
                .padding(16.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                FloatingActionButton(
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .size(40.dp),
                    contentColor = colorResource(R.color.pale_sky_blue),
                    containerColor = colorResource(R.color.charcoal_gray),
                    shape = RoundedCornerShape(10.dp),
                    onClick = goBack,
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.close),
                        contentDescription = null,
                    )
                }

                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    applyActionButton?.invoke(this@Box)
                }

                FloatingActionButton(
                    modifier = Modifier.size(40.dp),
                    contentColor = colorResource(R.color.pale_sky_blue),
                    containerColor = colorResource(R.color.charcoal_gray),
                    shape = RoundedCornerShape(10.dp),
                    onClick = onFlashIconClick,
                ) {
                    Icon(
                        painter = painterResource(
                            id = if (flashEnabled) R.drawable.flashlight_on
                            else R.drawable.flashlight_off
                        ),
                        contentDescription = null,
                    )
                }
            }
        }
    }
}

@Preview(
    showBackground = true,
    device = Devices.PIXEL_4,
    showSystemUi = true
)
@Composable
fun CameraViewPreview() {
    val context = LocalContext.current
    val previewView = remember {
        PreviewView(context).apply {
            // Set a background color to simulate camera preview
            setBackgroundColor(Color.DarkGray.toArgb())
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        }
    }

    var flashEnabled by remember { mutableStateOf(false) }

    CameraView(
        previewView = previewView,
        flashEnabled = flashEnabled,
        onFlashIconClick = { flashEnabled = !flashEnabled },
        goBack = { /* Preview only */ },
        applyActionButton = {
            FloatingActionButton(
                modifier = Modifier.size(56.dp),
                containerColor = MaterialTheme.colorScheme.primary,
                onClick = { /* Preview only */ }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.take_photo_button),
                    contentDescription = null,
                    tint = Color.White
                )
            }
        }
    )
}
