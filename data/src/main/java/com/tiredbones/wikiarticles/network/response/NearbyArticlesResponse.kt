package com.tiredbones.wikiarticles.network.response

import com.google.gson.annotations.SerializedName

data class NearbyArticlesResponse(
    @SerializedName("query") val query: GeosearchResponse,
    @SerializedName("batchcomplete") val batchcomplete: String?
)
