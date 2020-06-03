package com.tiredbones.wikiarticles.entities

data class GeoLocation(
    val lat: Double,
    val lon: Double
) {

  override fun toString(): String ="$lat|$lon"
}
