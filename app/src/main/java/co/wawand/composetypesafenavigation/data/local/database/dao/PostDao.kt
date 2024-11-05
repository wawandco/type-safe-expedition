package co.wawand.composetypesafenavigation.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import co.wawand.composetypesafenavigation.data.local.database.entity.PostAndAuthor
import co.wawand.composetypesafenavigation.data.local.database.entity.PostEntity

@Dao
interface PostDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPosts(posts: List<PostEntity>)

    @Transaction
    @Query("SELECT * FROM posts")
    suspend fun getPosts(): List<PostAndAuthor>

    @Transaction
    @Query("SELECT * FROM posts WHERE id = :id")
    suspend fun getPostById(id: Long): PostAndAuthor

    @Transaction
    @Query("SELECT * FROM posts WHERE authorOwnerId = :authorOwnerId")
    suspend fun getPostsByAuthor(authorOwnerId: Long): List<PostAndAuthor>
}