package co.wawand.composetypesafenavigation.domain.model

import android.net.Uri

sealed class BasePhoto(
    open val id: Long,
    open val title: String,
    open val imageSource: ImageSource
)

sealed class ImageSource {
    data class Remote(val thumbnailUrl: String, val url: String) : ImageSource()
    data class Local(val path: String) : ImageSource()
    data class Temporary(val uri: Uri) : ImageSource()
}