package co.wawand.composetypesafenavigation.di

import co.wawand.composetypesafenavigation.data.repository.AlbumRepositoryImpl
import co.wawand.composetypesafenavigation.data.repository.AuthorRepositoryImpl
import co.wawand.composetypesafenavigation.data.repository.PostRepositoryImpl
import co.wawand.composetypesafenavigation.data.repository.UserPreferencesRepositoryImpl
import co.wawand.composetypesafenavigation.data.repository.UserRepositoryImpl
import co.wawand.composetypesafenavigation.domain.repository.AlbumRepository
import co.wawand.composetypesafenavigation.domain.repository.AuthorRepository
import co.wawand.composetypesafenavigation.domain.repository.PostRepository
import co.wawand.composetypesafenavigation.domain.repository.UserPreferencesRepository
import co.wawand.composetypesafenavigation.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository

    @Binds
    @Singleton
    abstract fun bindPostRepository(postRepositoryImpl: PostRepositoryImpl): PostRepository

    @Binds
    @Singleton
    abstract fun bindUserPreferencesRepository(
        userPreferencesRepositoryImpl: UserPreferencesRepositoryImpl
    ): UserPreferencesRepository

    @Binds
    @Singleton
    abstract fun bindAlbumRepository(albumRepositoryImpl: AlbumRepositoryImpl): AlbumRepository

    @Binds
    @Singleton
    abstract fun bindAuthorRepository(authorRepositoryImpl: AuthorRepositoryImpl): AuthorRepository

}