package co.wawand.composetypesafenavigation.presentation.screens.details.album

import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.onLongClick
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import co.wawand.composetypesafenavigation.R
import co.wawand.composetypesafenavigation.domain.model.Photo
import co.wawand.composetypesafenavigation.presentation.extensions.photoGridDragHandler
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive


@Composable
fun PhotosGrid(
    photos: List<Photo> = emptyList(),
    selectedIds: MutableState<Set<Long>> = rememberSaveable { mutableStateOf(emptySet()) },
    onPhotoClicked: (Photo) -> Unit = {}
) {
    val inSelectionMode by remember { derivedStateOf { selectedIds.value.isNotEmpty() } }
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

    LazyVerticalGrid(
        state = lazyGridState,
        columns = GridCells.Adaptive(minSize = 128.dp),
        verticalArrangement = Arrangement.spacedBy(3.dp),
        horizontalArrangement = Arrangement.spacedBy(3.dp),
        modifier = Modifier
            .photoGridDragHandler(
                lazyGridState = lazyGridState,
                haptics = LocalHapticFeedback.current,
                selectedIds = selectedIds,
                autoScrollSpeed = autoScrollSpeed,
                autoScrollThreshold = with(LocalDensity.current) { 40.dp.toPx() }
            )
    ) {
        items(
            items = photos,
            key = { it.id }
        ) { photo ->

            val selected by remember {
                derivedStateOf { selectedIds.value.contains(photo.id) }
            }

            ImageItem(
                photo = photo,
                inSelectionMode = inSelectionMode,
                selected = selected,
                modifier = Modifier
                    .semantics {
                        if (!inSelectionMode) {
                            onLongClick("Select") {
                                selectedIds.value += photo.id
                                true
                            }
                        }
                    }
                    .clickable(onClick = { onPhotoClicked(photo) })
                    .then(
                        if (inSelectionMode) {
                            Modifier
                                .toggleable(
                                    value = selected,
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = null, // do not show a ripple
                                    onValueChange = {
                                        if (it) {
                                            selectedIds.value += photo.id
                                        } else {
                                            selectedIds.value -= photo.id
                                        }
                                    }
                                )
                        } else Modifier
                    )
            )
        }
    }
}


@Composable
private fun ImageItem(
    photo: Photo,
    inSelectionMode: Boolean,
    selected: Boolean,
    modifier: Modifier = Modifier,
) {
    val painter = rememberAsyncImagePainter(photo.thumbnailUrl)
    val painterState by painter.state.collectAsState()

    Surface(
        modifier = modifier
            .aspectRatio(1f),
        tonalElevation = 3.dp
    ) {
        Box(contentAlignment = if (inSelectionMode) Alignment.TopStart else Alignment.Center) {
            val transition = updateTransition(selected, label = null)
            val padding by transition.animateDp(label = stringResource(id = R.string.details_album_screen_padding_label)) { selected ->
                if (selected) 10.dp else 0.dp
            }
            val roundedCornerShape by transition.animateDp(label = stringResource(id = R.string.details_album_screen_corner_label)) { selected ->
                if (selected) 16.dp else 0.dp
            }
            when (painterState) {
                is AsyncImagePainter.State.Empty -> {}
                is AsyncImagePainter.State.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.width(42.dp),
                        color = MaterialTheme.colorScheme.secondary,
                        trackColor = MaterialTheme.colorScheme.surfaceVariant,
                    )
                }

                is AsyncImagePainter.State.Error -> {
                    Text(
                        text = stringResource(id = R.string.details_album_screen_unable_load_photo),
                        modifier = Modifier.padding(16.dp),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                is AsyncImagePainter.State.Success -> {
                    Image(
                        painter = painter,
                        contentDescription = photo.title,
                        modifier = Modifier
                            .matchParentSize()
                            .padding(padding)
                            .clip(RoundedCornerShape(roundedCornerShape))
                    )
                    if (inSelectionMode) {
                        if (selected) {
                            val bgColor = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp)
                            Icon(
                                imageVector = Icons.Filled.CheckCircle,
                                tint = MaterialTheme.colorScheme.primary,
                                contentDescription = null,
                                modifier = Modifier
                                    .padding(4.dp)
                                    .border(2.dp, bgColor, CircleShape)
                                    .clip(CircleShape)
                                    .background(bgColor)
                            )
                        } else {
                            Icon(
                                painter = painterResource(id = R.drawable.radio_button_unchecked),
                                tint = Color.White.copy(alpha = 0.7f),
                                contentDescription = null,
                                modifier = Modifier.padding(6.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}