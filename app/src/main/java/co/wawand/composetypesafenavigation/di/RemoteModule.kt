package co.wawand.composetypesafenavigation.di

import co.wawand.composetypesafenavigation.core.Constant
import co.wawand.composetypesafenavigation.data.remote.api.AlbumAPIService
import co.wawand.composetypesafenavigation.data.remote.api.AuthorAPIService
import co.wawand.composetypesafenavigation.data.remote.api.PostAPIService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {

    @Provides
    fun provideBaseUrl(): String = Constant.BASE_URL

    @Singleton
    @Provides
    fun provideRetrofit(baseUrl: String): Retrofit = Retrofit.Builder().baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Singleton
    @Provides
    fun providePostAPIClient(retrofit: Retrofit): PostAPIService {
        return retrofit.create(PostAPIService::class.java)
    }

    @Singleton
    @Provides
    fun provideAlbumAPIClient(retrofit: Retrofit): AlbumAPIService {
        return retrofit.create(AlbumAPIService::class.java)
    }

    @Singleton
    @Provides
    fun provideAuthorAPIServiceClient(retrofit: Retrofit): AuthorAPIService {
        return retrofit.create(AuthorAPIService::class.java)
    }
}