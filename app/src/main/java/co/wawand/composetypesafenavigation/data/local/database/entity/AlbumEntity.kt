package co.wawand.composetypesafenavigation.data.local.database.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "albums")
data class AlbumEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val title: String,
    val ownerId: Long,
)

data class AlbumAndOwner(
    @Embedded
    val albumEntity: AlbumEntity,

    @Relation(
        parentColumn = "ownerId",
        entityColumn = "id"
    )
    val ownerEntity: AuthorEntity
)

data class AlbumWithPhotosAndOwner(
    @Embedded
    val albumEntity: AlbumEntity,

    @Relation(
        parentColumn = "id",
        entityColumn = "albumId"
    )
    val photoEntityList: List<PhotoEntity>,

    @Relation(
        parentColumn = "ownerId",
        entityColumn = "id"
    )
    val ownerEntity: AuthorEntity?
)