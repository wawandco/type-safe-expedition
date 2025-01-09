package co.wawand.composetypesafenavigation.presentation.screens.user_photos

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import co.wawand.composetypesafenavigation.R
import co.wawand.composetypesafenavigation.domain.model.LocalPhoto
import co.wawand.composetypesafenavigation.presentation.navigation.NavigationEvent
import co.wawand.composetypesafenavigation.presentation.screens.lib.alert.InfoDialog
import co.wawand.composetypesafenavigation.presentation.screens.lib.camera.ImagePreview
import co.wawand.composetypesafenavigation.presentation.screens.lib.grid.PhotosGrid
import co.wawand.composetypesafenavigation.presentation.screens.lib.layout.AppLayout
import co.wawand.composetypesafenavigation.presentation.screens.lib.layout.NavigationConfiguration
import co.wawand.composetypesafenavigation.presentation.screens.lib.loading.ResourceLoading
import co.wawand.composetypesafenavigation.presentation.screens.lib.pop.AnimatedPopup
import co.wawand.composetypesafenavigation.presentation.screens.lib.text.UiText
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun UserPhotosScreen(
    onNavigate: (NavigationEvent) -> Unit,
    onEvent: (UserPhotosEvents) -> Unit,
    state: UserPhotosScreenState
) {
    val permissionState = rememberPermissionState(permission = Manifest.permission.CAMERA)
    val requestPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            onNavigate(NavigationEvent.OnNavigateToAddPhoto)
        } else {
            onEvent(UserPhotosEvents.HandleCameraPermissionError(UiText.StringResource(R.string.user_photos_screen_camera_permission_error)))
        }
    }

    LaunchedEffect(Unit) {
        onEvent(UserPhotosEvents.SetCameraPermission(permissionState.status.isGranted))
    }

    AppLayout(
        titleResId = R.string.user_photos_screen_main_label,
        navigationConfiguration = NavigationConfiguration(
            onClick = { onNavigate(NavigationEvent.OnNavigateToHome) },
            icon = Icons.AutoMirrored.Filled.ArrowBack
        ),
        floatingActionButton = {
            FloatingActionButton(onClick = {
                if (state.selectedIds.isEmpty()) {
                    if (state.showCamera && state.hasCameraPermission) {
                        onNavigate(NavigationEvent.OnNavigateToAddPhoto)
                    } else {
                        requestPermissionLauncher.launch(Manifest.permission.CAMERA)
                    }
                } else{
                    onEvent(UserPhotosEvents.OnDeleteClicked(state.selectedIds))
                }
            }) {
                if (state.selectedIds.isEmpty()) {
                    Icon(
                        painter = painterResource(id = R.drawable.add_a_photo),
                        contentDescription = null
                    )
                } else {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = null)
                }
            }
        }
    ) {
        if (state.isLoading) {
            ResourceLoading()
        } else {

            if (state.error != UiText.DynamicString("")) {
                InfoDialog(
                    message = state.error,
                    onDismissClick = { onEvent(UserPhotosEvents.ClearError) })
            }

            if (state.selectedPhoto != null) {
                AnimatedPopup(
                    onDismissRequest = { onEvent(UserPhotosEvents.OnHidePhotoPreview) },
                    properties = PopupProperties(
                        focusable = true,
                        dismissOnBackPress = true,
                        dismissOnClickOutside = false,
                        excludeFromSystemGesture = false,
                    )
                ) {
                    ImagePreview(
                        photo = state.selectedPhoto,
                        onBackAction = { onEvent(UserPhotosEvents.OnHidePhotoPreview) }
                    )
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.Transparent)
                    .padding(8.dp)
            ) {
                if (state.photos.isEmpty()) {
                    Text(
                        text = stringResource(id = R.string.user_photos_screen_no_photos_to_show),
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                } else {
                    PhotosGrid(
                        photos = state.photos,
                        selectedIds = state.selectedIds,
                        onSelectionChanged = {
                            onEvent(UserPhotosEvents.UpdatePhotoSelection(it))
                        },
                        onPhotoClicked = { photo ->
                            println("selected ids ${state.selectedIds}")
                            onEvent(UserPhotosEvents.OnPhotoClicked(photo))
                        }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun UserPhotosScreenPreview() {
    val context = LocalContext.current
    UserPhotosScreen(
        onNavigate = {},
        onEvent = {},
        state = UserPhotosScreenState(
            photos = listOf(
                LocalPhoto(
                    id = 1,
                    title = "title 1",
                    path = "android.resource://${context.packageName}/${R.drawable.ic_launcher_background}",
                    size = 100,
                    lastModified = 200
                ),
                LocalPhoto(
                    id = 2,
                    title = "title 1",
                    path = "android.resource://${context.packageName}/${R.drawable.ic_launcher_foreground}",
                    size = 100,
                    lastModified = 200
                ),
            )
        )
    )
}

@Preview
@Composable
fun UserPhotosScreenPreviewEmpty() {
    UserPhotosScreen(
        onNavigate = {},
        onEvent = {},
        state = UserPhotosScreenState()
    )
}

@Preview
@Composable
fun UserPhotosScreenPreviewPhotoSelected() {
    val context = LocalContext.current
    UserPhotosScreen(
        onNavigate = {},
        onEvent = {},
        state = UserPhotosScreenState(
            photos = listOf(
                LocalPhoto(
                    id = 1,
                    title = "title 1",
                    path = "android.resource://${context.packageName}/${R.drawable.ic_launcher_background}",
                    size = 100,
                    lastModified = 200
                ),
                LocalPhoto(
                    id = 2,
                    title = "title 1",
                    path = "android.resource://${context.packageName}/${R.drawable.ic_launcher_foreground}",
                    size = 100,
                    lastModified = 200
                ),
            ),
            selectedPhoto = LocalPhoto(
                id = 1,
                title = "title 1",
                path = "android.resource://${context.packageName}/${R.drawable.ic_launcher_background}",
                size = 100,
                lastModified = 200
            )
        )
    )
}

