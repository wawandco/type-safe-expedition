package co.wawand.composetypesafenavigation.domain.model

import androidx.compose.runtime.Immutable

@Immutable
data class RemotePhoto(
    override val id: Long,
    override val title: String,
    val thumbnailUrl: String,
    val url: String,
    val album: String,
) : BasePhoto(
    id = id,
    title = title,
    imageSource = ImageSource.Remote(thumbnailUrl, url)
)
