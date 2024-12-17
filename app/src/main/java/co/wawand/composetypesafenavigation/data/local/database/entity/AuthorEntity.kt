package co.wawand.composetypesafenavigation.data.local.database.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "authors")
data class AuthorEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val name: String,
    val username: String,
    val email: String,

    @ColumnInfo("createdAt") var createdAt: Long = System.currentTimeMillis(),
    @ColumnInfo("updatedAt") var updatedAt: Long = System.currentTimeMillis(),
)

data class AuthorWithAlbumsAndPosts(
    @Embedded
    val author: AuthorEntity,

    @Relation(
        parentColumn = "id",
        entityColumn = "ownerId"
    )
    val albums: List<AlbumEntity>?,

    @Relation(
        parentColumn = "id",
        entityColumn = "authorOwnerId"
    )
    val posts: List<PostEntity>?,
)