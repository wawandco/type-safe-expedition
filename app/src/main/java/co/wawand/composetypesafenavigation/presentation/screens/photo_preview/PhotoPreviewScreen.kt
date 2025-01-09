package co.wawand.composetypesafenavigation.presentation.screens.photo_preview

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import co.wawand.composetypesafenavigation.R
import co.wawand.composetypesafenavigation.domain.model.TemporaryPhoto
import co.wawand.composetypesafenavigation.presentation.navigation.NavigationEvent
import co.wawand.composetypesafenavigation.presentation.screens.lib.camera.ImagePreview
import java.io.File

@Composable
fun PhotoPreviewScreen(
    uriString: String,
    onNavigate: (NavigationEvent) -> Unit,
    onEvent: (PhotoPreviewScreenEvents) -> Unit,
    state: PhotoPreviewScreenState,
) {
    val photoUri = Uri.parse(uriString)
    LaunchedEffect(key1 = uriString) {
        onEvent(PhotoPreviewScreenEvents.LoadPhotoUri(uriString))
    }

    if (state.isPhotoSaved) {
        LaunchedEffect(Unit) {
            onNavigate(NavigationEvent.OnNavigateToUserPhotos)
        }
    }

    LaunchedEffect(state.navigation) {
        when (state.navigation) {
            is PhotoPreviewNavigation.NavigateToAddPhoto -> onNavigate(NavigationEvent.OnNavigateToAddPhoto)

            is PhotoPreviewNavigation.NavigateToUserPhotos -> onNavigate(NavigationEvent.OnNavigateToUserPhotos)

            PhotoPreviewNavigation.None -> {}
        }
    }

    ImagePreview(
        photo = TemporaryPhoto(uri = photoUri),
        isLoading = state.isLoading,
        error = state.error,
        clearError = { onEvent(PhotoPreviewScreenEvents.ClearError) },
        onCloseAction = {
            onEvent(
                PhotoPreviewScreenEvents.RemovePreviewAndRedirect(
                    photoFilePreview = File(photoUri.path!!),
                    navigateTo = PhotoPreviewNavigation.NavigateToUserPhotos
                )
            )
        },
        leftSideButton = Pair(stringResource(id = R.string.user_photos_screen_retake_photo)) {
            onEvent(
                PhotoPreviewScreenEvents.RemovePreviewAndRedirect(
                    photoFilePreview = File(photoUri.path!!),
                    navigateTo = PhotoPreviewNavigation.NavigateToAddPhoto
                )
            )
        },
        rightSideButton = Pair(stringResource(id = R.string.user_photos_screen_confirm)) {
            onEvent(PhotoPreviewScreenEvents.OnPersistUserPhoto)
        }
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PhotoPreviewScreenPreview() {
    PhotoPreviewScreen(
        uriString = "",
        onNavigate = {},
        onEvent = {},
        state = PhotoPreviewScreenState()
    )
}