package co.wawand.composetypesafenavigation.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import co.wawand.composetypesafenavigation.data.local.database.dao.AlbumDao
import co.wawand.composetypesafenavigation.data.local.database.dao.AuthorDao
import co.wawand.composetypesafenavigation.data.local.database.dao.PhotoDao
import co.wawand.composetypesafenavigation.data.local.database.dao.PostDao
import co.wawand.composetypesafenavigation.data.local.database.dao.UserDao
import co.wawand.composetypesafenavigation.data.local.database.entity.AlbumEntity
import co.wawand.composetypesafenavigation.data.local.database.entity.AuthorEntity
import co.wawand.composetypesafenavigation.data.local.database.entity.PhotoEntity
import co.wawand.composetypesafenavigation.data.local.database.entity.PostEntity
import co.wawand.composetypesafenavigation.data.local.database.entity.UserEntity

@Database(
    entities = [
        UserEntity::class,
        AuthorEntity::class,
        PostEntity::class,
        AlbumEntity::class,
        PhotoEntity::class
    ],
    version = 1,
    exportSchema = false,
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDAO(): UserDao

    abstract fun authorDAO(): AuthorDao

    abstract fun postDAO(): PostDao

    abstract fun albumDAO(): AlbumDao

    abstract fun photoDAO(): PhotoDao

}