package co.wawand.composetypesafenavigation.data.mapper

import co.wawand.composetypesafenavigation.data.local.database.entity.PhotoEntity
import co.wawand.composetypesafenavigation.data.local.database.entity.PhotoType
import co.wawand.composetypesafenavigation.data.local.database.entity.PhotoWithAlbum
import co.wawand.composetypesafenavigation.data.remote.api.entity.PhotoAPIEntity
import co.wawand.composetypesafenavigation.domain.model.BasePhoto
import co.wawand.composetypesafenavigation.domain.model.LocalPhoto
import co.wawand.composetypesafenavigation.domain.model.RemotePhoto


fun PhotoAPIEntity.toDBEntity(): PhotoEntity {
    val randomNumber = (0..100000).random()
    return PhotoEntity(
        id = id,
        title = title,
        type = PhotoType.REMOTE,
        thumbnailUrl = "https://picsum.photos/seed/$randomNumber/256/256",
        url = "https://picsum.photos/seed/$randomNumber/2400/1600",
        albumId = albumId
    )
}

fun PhotoEntity.toDomain(): BasePhoto {
    return when (type) {
        PhotoType.LOCAL -> LocalPhoto(
            id = id,
            title = title,
            path = path!!,
            size = size!!,
            lastModified = lastModified!!
        )

        PhotoType.REMOTE -> RemotePhoto(
            id = id,
            title = title,
            thumbnailUrl = thumbnailUrl!!,
            url = url!!,
            album = albumId.toString()
        )
    }
}

fun PhotoWithAlbum.toDomain(): RemotePhoto {
    val photo = this.photoEntity
    val album = this.albumEntity

    return RemotePhoto(
        id = photo.id,
        title = photo.title,
        thumbnailUrl = photo.thumbnailUrl!!,
        url = photo.url!!,
        album = album?.title ?: ""
    )
}

fun PhotoEntity.toPhotoDetailsEntity(): LocalPhoto = LocalPhoto(
    id = id,
    title = title,
    path = path!!,
    size = size!!,
    lastModified = lastModified!!
)

fun LocalPhoto.toEntity(userId: Long): PhotoEntity = PhotoEntity(
    id = id,
    title = title,
    type = PhotoType.LOCAL,
    path = path,
    size = size,
    lastModified = lastModified,
    userId = userId,
)