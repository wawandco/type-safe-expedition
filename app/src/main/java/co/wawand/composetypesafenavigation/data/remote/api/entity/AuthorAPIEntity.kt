package co.wawand.composetypesafenavigation.data.remote.api.entity

import com.google.gson.annotations.SerializedName

data class AuthorAPIEntity(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String,
    @SerializedName("username") val username: String,
    @SerializedName("email") val email: String,
)