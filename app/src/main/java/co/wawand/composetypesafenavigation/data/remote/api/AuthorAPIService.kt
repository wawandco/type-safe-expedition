package co.wawand.composetypesafenavigation.data.remote.api

import co.wawand.composetypesafenavigation.data.remote.api.entity.AuthorAPIEntity
import retrofit2.Response
import retrofit2.http.GET

interface AuthorAPIService {

    @GET("users")
    suspend fun getAuthors(): Response<List<AuthorAPIEntity>>
}