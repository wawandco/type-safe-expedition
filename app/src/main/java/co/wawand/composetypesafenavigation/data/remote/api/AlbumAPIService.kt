package co.wawand.composetypesafenavigation.data.remote.api

import co.wawand.composetypesafenavigation.data.remote.api.entity.AlbumAPIEntity
import co.wawand.composetypesafenavigation.data.remote.api.entity.PhotoAPIEntity
import retrofit2.Response
import retrofit2.http.GET

interface AlbumAPIService {

    @GET("/albums")
    suspend fun getAlbums(): Response<List<AlbumAPIEntity>>

    @GET("/photos")
    suspend fun getPhotos(): Response<List<PhotoAPIEntity>>
}