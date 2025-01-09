package co.wawand.composetypesafenavigation.domain.model

import androidx.compose.runtime.Immutable

@Immutable
data class LocalPhoto(
    override val id: Long,
    override val title: String,
    val path: String,
    val size: Long,
    val lastModified: Long,
) : BasePhoto(
    id = id,
    title = title,
    imageSource = ImageSource.Local(path)
)