package co.wawand.composetypesafenavigation.presentation.screens.user_photos

import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import co.wawand.composetypesafenavigation.domain.model.LocalPhoto
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive

@Composable
fun UserPhotosGrid(
    photos: List<LocalPhoto> = emptyList(),
    selectedPhotos: MutableState<Set<String>> = rememberSaveable { mutableStateOf(emptySet()) },
    onPhotoClicked: (LocalPhoto) -> Unit = {}
) {
    val inSelectionMode by remember { derivedStateOf { selectedPhotos.value.isNotEmpty() } }
    val lazyGridState = rememberLazyGridState()
    val autoScrollSpeed = remember { mutableFloatStateOf(0f) }

    LaunchedEffect(autoScrollSpeed.floatValue) {
        if (autoScrollSpeed.floatValue != 0f) {
            while (isActive) {
                lazyGridState.scrollBy(autoScrollSpeed.floatValue)
                delay(10)
            }
        }
    }


}