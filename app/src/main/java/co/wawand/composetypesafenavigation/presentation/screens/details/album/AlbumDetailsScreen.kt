package co.wawand.composetypesafenavigation.presentation.screens.details.album

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import co.wawand.composetypesafenavigation.R
import co.wawand.composetypesafenavigation.presentation.navigation.NavigationEvent
import co.wawand.composetypesafenavigation.presentation.screens.lib.grid.PhotosGrid
import co.wawand.composetypesafenavigation.presentation.screens.lib.layout.AppLayout
import co.wawand.composetypesafenavigation.presentation.screens.lib.layout.NavigationConfiguration
import co.wawand.composetypesafenavigation.presentation.screens.lib.loading.ResourceLoading
import co.wawand.composetypesafenavigation.presentation.utils.mockdata.generateStaticAlbumWithPhotos
import co.wawand.composetypesafenavigation.presentation.utils.mockdata.generateStaticPhotos
import coil3.annotation.ExperimentalCoilApi
import coil3.compose.AsyncImagePreviewHandler
import coil3.compose.LocalAsyncImagePreviewHandler
import coil3.test.FakeImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlbumDetailsScreen(
    albumId: Long,
    onNavigate: (NavigationEvent) -> Unit,
    onEvent: (AlbumDetailsScreenEvents) -> Unit,
    state: AlbumDetailsScreenState,
) {
    LaunchedEffect(key1 = albumId) {
        onEvent(AlbumDetailsScreenEvents.LoadAlbumDetails(albumId))
    }

    AppLayout(
        titleResId = R.string.details_album_screen_main_label,
        navigationConfiguration = NavigationConfiguration(
            onClick = { onNavigate(NavigationEvent.OnNavigateUp) },
            icon = Icons.AutoMirrored.Filled.ArrowBack
        ),
        floatingActionButton = {
            if (state.selectedIds.isNotEmpty()) {
                FloatingActionButton(onClick = {
                    onEvent(AlbumDetailsScreenEvents.OnDeleteClicked(state.selectedIds, albumId))
                }) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = null)
                }
            }
        }
    ) {
        if (state.isLoading) {
            ResourceLoading()
        } else {
            if (state.selectedPhoto != null) {
                ImageDetail(
                    onDismissRequest = { onEvent(AlbumDetailsScreenEvents.OnDismissPhotoDialog) },
                    photo = state.selectedPhoto
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.Transparent),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .background(color = Color.Transparent)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            modifier = Modifier.padding(4.dp),
                            text = stringResource(id = R.string.details_album_screen_album_label),
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Light,
                        )
                        Text(
                            modifier = Modifier.padding(4.dp),
                            text = state.album?.title
                                ?: stringResource(id = R.string.home_screen_unknown_label),
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            modifier = Modifier.padding(4.dp),
                            text = stringResource(id = R.string.home_screen_author_label),
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Light,
                        )
                        Text(
                            modifier = Modifier.padding(4.dp),
                            text = state.album?.owner?.name
                                ?: stringResource(id = R.string.home_screen_unknown_label),
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                }
                PhotosGrid(
                    photos = state.album?.photos ?: emptyList(),
                    selectedIds = state.selectedIds,
                    onSelectionChanged = {
                        onEvent(AlbumDetailsScreenEvents.UpdatePhotoSelection(it))
                    },
                    onPhotoClicked = { photo ->
                        onEvent(AlbumDetailsScreenEvents.OnPhotoClicked(photo))
                    }
                )
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun AlbumDetailsScreenPreview() {
    AlbumDetailsScreen(
        albumId = 1L,
        onNavigate = {},
        onEvent = {},
        state = AlbumDetailsScreenState(
            album = generateStaticAlbumWithPhotos()
        )
    )
}

@OptIn(ExperimentalCoilApi::class)
@Preview(showBackground = true)
@Composable
fun AlbumDetailsScreenPreviewPhotoClicked() {
    val previewHandler = AsyncImagePreviewHandler {
        FakeImage(color = 0xFFFF0000.toInt())
    }
    CompositionLocalProvider(LocalAsyncImagePreviewHandler provides previewHandler) {
        AlbumDetailsScreen(
            albumId = 1L,
            onNavigate = {},
            onEvent = {},
            state = AlbumDetailsScreenState(
                album = generateStaticAlbumWithPhotos(),
                selectedPhoto = generateStaticPhotos()[0]
            )
        )
    }
}