package co.wawand.composetypesafenavigation.data.remote.api.entity

import com.google.gson.annotations.SerializedName

data class PhotoAPIEntity(
    @SerializedName("id") val id: Long,
    @SerializedName("title") val title: String,
    @SerializedName("url") val url: String,
    @SerializedName("thumbnailUrl") val thumbnailUrl: String,
    @SerializedName("albumId") val albumId: Long,
)