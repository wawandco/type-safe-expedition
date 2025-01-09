package co.wawand.composetypesafenavigation.data.service

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import co.wawand.composetypesafenavigation.R
import co.wawand.composetypesafenavigation.domain.service.FileService
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import javax.inject.Inject

class FileServiceImpl @Inject constructor(
    @ApplicationContext private val context: Context,
) : FileService {

    override fun getPhotosDirectory(): File {
        val mediaDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        File(mediaDir, context.getString(R.string.app_name)).apply { mkdirs() }

        return if (mediaDir != null && mediaDir.exists()) mediaDir else context.filesDir
    }

    override fun generatePhotoFile(prefix: String): File {
        return File(
            getPhotosDirectory(),
            "${prefix}_${System.currentTimeMillis()}.jpg"
        ).apply {
            absolutePath
        }
    }
}