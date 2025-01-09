package co.wawand.composetypesafenavigation.di

import co.wawand.composetypesafenavigation.data.service.FileServiceImpl
import co.wawand.composetypesafenavigation.domain.service.FileService
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ServiceModule {

    @Binds
    @Singleton
    abstract fun bindFileService(fileServiceImpl: FileServiceImpl): FileService
}