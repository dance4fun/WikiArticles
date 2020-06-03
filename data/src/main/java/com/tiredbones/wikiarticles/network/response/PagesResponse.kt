package com.tiredbones.wikiarticles.network.response

import com.google.gson.annotations.SerializedName

data class PagesResponse(
    @SerializedName("pages") val pages: Map<String, PagesItemResponse>
)

data class PagesItemResponse(
    @SerializedName("pageid") val pageid: String,
    @SerializedName("ns") val ns: String,
    @SerializedName("title") val title: String,
    @SerializedName("fullurl") val fullurl: String,
    @SerializedName("description") val description: String?,
    @SerializedName("original") val original: ImageItemResponse?
)

data class ImageItemResponse(
    @SerializedName("source") val source: String
)
