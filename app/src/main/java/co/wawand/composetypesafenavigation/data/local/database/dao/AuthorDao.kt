package co.wawand.composetypesafenavigation.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import co.wawand.composetypesafenavigation.data.local.database.entity.AuthorEntity
import co.wawand.composetypesafenavigation.data.local.database.entity.AuthorWithAlbumsAndPosts

@Dao
interface AuthorDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAuthors(authors: List<AuthorEntity>)

    @Query("SELECT * FROM authors")
    fun getAuthors(): List<AuthorWithAlbumsAndPosts>

}