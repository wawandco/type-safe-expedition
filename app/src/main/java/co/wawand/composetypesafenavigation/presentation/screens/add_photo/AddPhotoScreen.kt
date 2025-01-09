package co.wawand.composetypesafenavigation.presentation.screens.add_photo

import android.Manifest
import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import co.wawand.composetypesafenavigation.presentation.navigation.NavigationEvent
import co.wawand.composetypesafenavigation.presentation.screens.lib.camera.UserCameraView
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@OptIn(ExperimentalPermissionsApi::class)
@SuppressLint("UnrememberedMutableState")
@Composable
fun AddPhotoScreen(
    onNavigate: (NavigationEvent) -> Unit,
    onEvent: (AddPhotoScreenEvents) -> Unit,
    state: AddPhotoScreenState,
) {

    val cameraExecutor: ExecutorService = Executors.newSingleThreadExecutor()
    val cameraState = rememberPermissionState(permission = Manifest.permission.CAMERA)

    if (cameraState.status.isGranted && state.showCamera) {
        UserCameraView(
            executor = cameraExecutor,
            onImageCaptured = {
                onEvent(AddPhotoScreenEvents.OnImageCaptured(it))
            },
            onGoBack = { onNavigate(NavigationEvent.OnNavigateUp) },
            flashMode = state.flashMode,
            setFlashMode = { onEvent(AddPhotoScreenEvents.OnFlashModeChanged) },
            imageCapture = state.imageCapture,
            takePhoto = { executor, imageCapture, onImageCaptured ->
                onEvent(AddPhotoScreenEvents.OnTakePhoto(executor, imageCapture, onImageCaptured))
            }
        )
    }

    if (state.showPreview) {
        onNavigate(NavigationEvent.OnNavigateToPhotoPreview(state.photoUri.toString()))
    }
}