package co.wawand.composetypesafenavigation.domain.model

import android.net.Uri
import androidx.compose.runtime.Immutable

@Immutable
data class TemporaryPhoto(
    override val id: Long = System.currentTimeMillis(),
    override val title: String = "",
    val uri: Uri
) : BasePhoto(
    id = id,
    title = title,
    imageSource = ImageSource.Temporary(uri)
)