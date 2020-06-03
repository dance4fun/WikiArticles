package com.tiredbones.wikiarticles.network.response

import com.google.gson.annotations.SerializedName

data class GeosearchResponse(
    @SerializedName("geosearch") val geosearch: List<GeosearchItemResponse>
)

data class GeosearchItemResponse(
    @SerializedName("pageid") val pageid: String,
    @SerializedName("ns") val ns: String,
    @SerializedName("title") val title: String,
    @SerializedName("lat") val lat: Double,
    @SerializedName("lon") val lon: Double,
    @SerializedName("dist") val dist: Double,
    @SerializedName("primary") val primary: String
)
