package co.wawand.composetypesafenavigation.data.local.database.dao

import androidx.room.Dao
import androidx.room.Upsert
import co.wawand.composetypesafenavigation.data.local.database.entity.UserEntity

@Dao
interface UserDao {

    @Upsert
    suspend fun upsertUser(user: UserEntity): Long
}