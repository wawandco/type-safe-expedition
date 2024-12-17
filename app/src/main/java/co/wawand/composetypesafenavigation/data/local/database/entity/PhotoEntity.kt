package co.wawand.composetypesafenavigation.data.local.database.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "photos")
data class PhotoEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val title: String,
    val thumbnailUrl: String,
    val url: String,
    val albumId: Long,
)

data class PhotoWithAlbum(
    @Embedded
    val photoEntity: PhotoEntity,

    @Relation(
        parentColumn = "albumId",
        entityColumn = "id"
    )
    val albumEntity: AlbumEntity
)