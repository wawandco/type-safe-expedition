package co.wawand.composetypesafenavigation.data.remote.api.entity

import com.google.gson.annotations.SerializedName

data class PostAPIEntity(
    @SerializedName("id") val id: Long,
    @SerializedName("title") val title: String,
    @SerializedName("body") val body: String,
    @SerializedName("userId") val userId: Long,
)