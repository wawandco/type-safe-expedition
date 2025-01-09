package co.wawand.composetypesafenavigation.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import co.wawand.composetypesafenavigation.data.local.database.entity.PhotoEntity
import co.wawand.composetypesafenavigation.data.local.database.entity.PhotoWithAlbum

@Dao
interface PhotoDao {

    @Upsert
    suspend fun upsertUserPhoto(userPhoto: PhotoEntity): Long

    @Transaction
    @Query("SELECT * FROM photos where userId = :id")
    suspend fun getPhotosByUserId(id: Long): List<PhotoEntity>

    @Upsert
    suspend fun insertPhotos(photos: List<PhotoEntity>)

    @Transaction
    @Query("SELECT * FROM photos")
    suspend fun getPhotos(): List<PhotoWithAlbum>

    @Transaction
    @Query("SELECT * FROM photos where id = :id")
    suspend fun getPhotoWithAlbumById(id: Long): PhotoWithAlbum

    @Transaction
    @Query("SELECT * FROM photos where id = :id")
    suspend fun getPhotoById(id: Long): PhotoEntity

    @Transaction
    @Query("SELECT path FROM photos where id = :id")
    suspend fun getPhotoPathById(id: Long): String

    @Transaction
    @Query("DELETE FROM photos where id = :id")
    suspend fun deletePhotoById(id: Long)
}