package co.wawand.composetypesafenavigation.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import co.wawand.composetypesafenavigation.data.local.database.entity.PhotoEntity
import co.wawand.composetypesafenavigation.data.local.database.entity.PhotoWithAlbum

@Dao
interface PhotoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPhotos(photos: List<PhotoEntity>)

    @Transaction
    @Query("SELECT * FROM photos")
    suspend fun getPhotos(): List<PhotoWithAlbum>

    @Transaction
    @Query("SELECT * FROM photos where id = :id")
    suspend fun getPhotoById(id: Long): PhotoWithAlbum

}