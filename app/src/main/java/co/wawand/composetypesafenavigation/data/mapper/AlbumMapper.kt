package co.wawand.composetypesafenavigation.data.mapper

import co.wawand.composetypesafenavigation.data.local.database.entity.AlbumEntity
import co.wawand.composetypesafenavigation.data.local.database.entity.AlbumWithPhotosAndOwner
import co.wawand.composetypesafenavigation.data.remote.api.entity.AlbumAPIEntity
import co.wawand.composetypesafenavigation.domain.model.Album

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