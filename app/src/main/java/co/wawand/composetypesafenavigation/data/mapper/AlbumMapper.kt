package co.wawand.composetypesafenavigation.data.mapper

import co.wawand.composetypesafenavigation.data.local.database.entity.AlbumEntity
import co.wawand.composetypesafenavigation.data.local.database.entity.AlbumWithPhotosAndOwner
import co.wawand.composetypesafenavigation.data.remote.api.entity.AlbumAPIEntity
import co.wawand.composetypesafenavigation.domain.model.Album
import co.wawand.composetypesafenavigation.domain.model.AlbumWithPhotos

fun AlbumAPIEntity.toDBEntity(): AlbumEntity = AlbumEntity(
    id = id,
    title = title,
    ownerId = userId
)

fun AlbumWithPhotosAndOwner.toDomain(): Album {
    val albumEntity = this.albumEntity
    val ownerEntity = this.ownerEntity
    val photoEntityList = this.photoEntityList

    return Album(
        id = albumEntity.id,
        title = albumEntity.title,
        photos = photoEntityList.size,
        owner = ownerEntity?.toDomain()
    )
}

fun List<AlbumWithPhotosAndOwner>.toDomain(): List<Album> {
    return this.map { it.toDomain() }
}

fun AlbumWithPhotosAndOwner.toAlbumWithPhotosDomain(): AlbumWithPhotos {
    val albumEntity = this.albumEntity
    val ownerEntity = this.ownerEntity
    val photoEntityList = this.photoEntityList

    return AlbumWithPhotos(
        id = albumEntity.id,
        title = albumEntity.title,
        photos = photoEntityList.map { it.toDomain() },
        owner = ownerEntity?.toDomain(),
    )
}