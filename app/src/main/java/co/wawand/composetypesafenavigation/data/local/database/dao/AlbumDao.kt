package co.wawand.composetypesafenavigation.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import co.wawand.composetypesafenavigation.data.local.database.entity.AlbumEntity
import co.wawand.composetypesafenavigation.data.local.database.entity.AlbumWithPhotosAndOwner

@Dao
interface AlbumDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlbums(albums: List<AlbumEntity>)

    @Transaction
    @Query("SELECT * FROM albums where id = :id")
    suspend fun getAlbumById(id: Long): AlbumWithPhotosAndOwner

    @Transaction
    @Query("SELECT * FROM albums")
    suspend fun getAlbumsWithPhotos(): List<AlbumWithPhotosAndOwner>
}