package com.tiredbones.wikiarticles.network.response

import com.google.gson.annotations.SerializedName

data class ArticlesDetailsResponse(
    @SerializedName("query") val query: PagesResponse,
    @SerializedName("batchcomplete") val batchcomplete: String?
)
