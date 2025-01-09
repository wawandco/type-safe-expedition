package co.wawand.composetypesafenavigation.domain.service

import java.io.File

interface FileService {
    fun getPhotosDirectory(): File
    fun generatePhotoFile(prefix: String): File
}