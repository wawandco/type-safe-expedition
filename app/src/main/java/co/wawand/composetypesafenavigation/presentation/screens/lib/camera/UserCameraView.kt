package co.wawand.composetypesafenavigation.presentation.screens.lib.camera

import android.net.Uri
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCapture.FLASH_MODE_ON
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.withStateAtLeast
import co.wawand.composetypesafenavigation.R
import java.util.concurrent.Executor

@Composable
fun UserCameraView(
    executor: Executor,
    onImageCaptured: (Uri) -> Unit,
    onGoBack: () -> Unit,
    flashMode: Int,
    setFlashMode: () -> Unit,
    imageCapture: ImageCapture,
    takePhoto: (Executor, ImageCapture, (Uri) -> Unit) -> Unit
) {
    val lensFacing = CameraSelector.LENS_FACING_BACK
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val preview = Preview.Builder().build()
    val previewView = remember { PreviewView(context) }
    val cameraSelector = CameraSelector.Builder().requireLensFacing(lensFacing).build()

    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }
    val cameraProvider = cameraProviderFuture.get()

    LaunchedEffect(lensFacing, flashMode) {
        lifecycleOwner.withStateAtLeast(Lifecycle.State.RESUMED) {
            cameraProviderFuture.addListener({
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    lifecycleOwner, cameraSelector, preview, imageCapture
                )
            }, context.mainExecutor)
            preview.surfaceProvider = previewView.surfaceProvider
        }
    }

    CameraView(
        previewView = previewView,
        flashEnabled = flashMode == FLASH_MODE_ON,
        onFlashIconClick = setFlashMode,
        goBack = onGoBack,
        applyActionButton = {
            IconButton(
                modifier = Modifier.align(Alignment.Center),
                onClick = {
                    takePhoto(executor, imageCapture, onImageCaptured)
                }
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