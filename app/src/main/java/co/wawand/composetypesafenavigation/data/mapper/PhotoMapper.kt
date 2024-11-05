package co.wawand.composetypesafenavigation.data.mapper

import co.wawand.composetypesafenavigation.data.local.database.entity.PhotoEntity
import co.wawand.composetypesafenavigation.data.local.database.entity.PhotoWithAlbum
import co.wawand.composetypesafenavigation.data.remote.api.entity.PhotoAPIEntity
import co.wawand.composetypesafenavigation.domain.model.Photo

fun PhotoAPIEntity.toDBEntity(): PhotoEntity = PhotoEntity(
    id = id,
    title = title,
    thumbnailUrl = thumbnailUrl,
    url = url,
    albumId = albumId
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