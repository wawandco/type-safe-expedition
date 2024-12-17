package co.wawand.composetypesafenavigation.di

import android.content.Context
import androidx.room.Room
import co.wawand.composetypesafenavigation.data.local.database.AppDatabase
import co.wawand.composetypesafenavigation.data.local.database.dao.AlbumDao
import co.wawand.composetypesafenavigation.data.local.database.dao.AuthorDao
import co.wawand.composetypesafenavigation.data.local.database.dao.PhotoDao
import co.wawand.composetypesafenavigation.data.local.database.dao.PostDao
import co.wawand.composetypesafenavigation.data.local.database.dao.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    private const val APP_DATABASE_NAME = "app_database"

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            APP_DATABASE_NAME
        )
            .build()

    @Provides
    fun providesUserDao(database: AppDatabase): UserDao = database.userDAO()

    @Provides
    fun providesAuthorDao(database: AppDatabase): AuthorDao = database.authorDAO()

    @Provides
    fun providesPostDao(database: AppDatabase): PostDao = database.postDAO()

    @Provides
    fun providesAlbumDao(database: AppDatabase): AlbumDao = database.albumDAO()

    @Provides
    fun providesPhotoDao(database: AppDatabase): PhotoDao = database.photoDAO()
}