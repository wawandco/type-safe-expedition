package co.wawand.composetypesafenavigation.data.mapper

import co.wawand.composetypesafenavigation.data.local.database.entity.PhotoEntity
import co.wawand.composetypesafenavigation.data.local.database.entity.PhotoWithAlbum
import co.wawand.composetypesafenavigation.data.remote.api.entity.PhotoAPIEntity
import co.wawand.composetypesafenavigation.domain.model.Photo

fun PhotoAPIEntity.toDBEntity(): PhotoEntity {
    val randomNumber = (0..100000).random()
    return PhotoEntity(
        id = id,
        title = title,
        thumbnailUrl = "https://picsum.photos/seed/$randomNumber/256/256",
        url = "https://picsum.photos/seed/$randomNumber/2400/1600",
        albumId = albumId
    )
}

fun PhotoEntity.toDomain(): Photo = Photo(
    id = id,
    title = title,
    thumbnailUrl = thumbnailUrl,
    url = url,
    album = albumId.toString()
)

fun PhotoWithAlbum.toDomain(): Photo {
    val photo = this.photoEntity
    val album = this.albumEntity

    return Photo(
        id = photo.id,
        title = photo.title,
        thumbnailUrl = photo.thumbnailUrl,
        url = photo.url,
        album = album.title
    )
}