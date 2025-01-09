package co.wawand.composetypesafenavigation.data.local.database.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "photos")
data class PhotoEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val title: String,
    val type: PhotoType,

    // Fields specific to remote photos
    val thumbnailUrl: String? = null,
    val url: String? = null,
    val albumId: Long? = null,

    // Fields specific to local photos
    val path: String? = null,
    val size: Long? = null,
    val lastModified: Long? = null,
    val userId: Long? = null,
)

enum class PhotoType {
    LOCAL,
    REMOTE
}

data class PhotoWithAlbum(
    @Embedded
    val photoEntity: PhotoEntity,

    @Relation(
        parentColumn = "albumId",
        entityColumn = "id"
    )
    val albumEntity: AlbumEntity?
)