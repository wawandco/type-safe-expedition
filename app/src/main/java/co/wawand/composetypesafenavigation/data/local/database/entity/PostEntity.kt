package co.wawand.composetypesafenavigation.data.local.database.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "posts")
data class PostEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val title: String = "",
    val body: String = "",
    val authorOwnerId: Long,
)

data class PostAndAuthor(
    @Embedded
    val post: PostEntity,

    @Relation(
        parentColumn = "authorOwnerId",
        entityColumn = "id"
    )
    val author: AuthorEntity?
)