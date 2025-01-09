package co.wawand.composetypesafenavigation.presentation.screens.lib.camera

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import co.wawand.composetypesafenavigation.R
import co.wawand.composetypesafenavigation.core.Constant
import co.wawand.composetypesafenavigation.domain.model.BasePhoto
import co.wawand.composetypesafenavigation.domain.model.ImageSource
import co.wawand.composetypesafenavigation.presentation.screens.lib.alert.InfoDialog
import co.wawand.composetypesafenavigation.presentation.screens.lib.loading.ResourceLoading
import co.wawand.composetypesafenavigation.presentation.screens.lib.text.UiText
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import coil3.request.crossfade

@Composable
fun ImagePreview(
    photo: BasePhoto,
    isLoading: Boolean = false,
    error: UiText = UiText.DynamicString(""),
    clearError: () -> Unit = {},
    onBackAction: () -> Unit = {},
    leftSideButton: Pair<String, () -> Unit> = Pair("") {},
    rightSideButton: Pair<String, () -> Unit> = Pair("") {},
) {
    val screenHeight = LocalConfiguration.current.screenHeightDp
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .systemBarsPadding()
            .background(Color.Black)
    ) {
        if (isLoading) {
            ResourceLoading()
        }
        if (error.asString(context).isNotEmpty()) {
            InfoDialog(
                onClick = clearError,
                onDismissClick = clearError,
                message =
                if (error.asString(context) == Constant.GENERIC_ERROR) UiText.StringResource(
                    resId = R.string.generic_error
                )
                else error,
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 15.dp)
                .height((screenHeight * 0.2).dp)
                .background(Color.Black),
        ) {
            FloatingActionButton(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .height(40.dp)
                    .width(40.dp),
                contentColor = colorResource(R.color.pale_sky_blue),
                containerColor = colorResource(R.color.charcoal_gray),
                shape = RoundedCornerShape(10.dp),
                onClick = onBackAction
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.close),
                    contentDescription = null,
                )
            }
        }
        when (val source = photo.imageSource) {
            is ImageSource.Remote -> {
                val painter = rememberAsyncImagePainter(source.url)
                val painterState by painter.state.collectAsState()
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
                        )
                    }
                }
            }

            is ImageSource.Local -> {
                AsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height((screenHeight * 0.5).dp)
                        .align(Alignment.Center),
                    model = ImageRequest.Builder(LocalContext.current).data(source.path)
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    contentScale = ContentScale.Inside,
                )
            }

            is ImageSource.Temporary -> {
                AsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height((screenHeight * 0.5).dp)
                        .align(Alignment.Center),
                    model = ImageRequest.Builder(context)
                        .data(source.uri)
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    contentScale = ContentScale.Inside,
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding(),
            contentAlignment = Alignment.BottomCenter
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height((screenHeight * 0.12).dp)
                    .background(colorResource(id = R.color.charcoal_gray)),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                TextButton(
                    modifier = Modifier.padding(
                        top = (screenHeight * 0.02).dp, start = 18.dp
                    ), colors = ButtonDefaults.buttonColors(
                        contentColor = colorResource(id = R.color.soft_lavender_blue),
                        containerColor = colorResource(
                            id = R.color.charcoal_gray
                        )
                    ), onClick = leftSideButton.second
                ) {
                    Text(
                        text = leftSideButton.first,
                        style = MaterialTheme.typography.labelLarge
                    )
                }

                TextButton(
                    modifier = Modifier.padding(top = (screenHeight * 0.02).dp, end = 18.dp),
                    colors = ButtonDefaults.buttonColors(
                        contentColor = colorResource(id = R.color.soft_lavender_blue),
                        containerColor = colorResource(
                            id = R.color.charcoal_gray
                        )
                    ),
                    onClick = rightSideButton.second
                ) {
                    Text(
                        text = rightSideButton.first,
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }
        }
    }
}