package co.wawand.composetypesafenavigation.data.remote.api

import co.wawand.composetypesafenavigation.data.remote.api.entity.PostAPIEntity
import retrofit2.Response
import retrofit2.http.GET

interface PostAPIService {

    @GET("posts")
    suspend fun getPosts(): Response<List<PostAPIEntity>>
}