package com.tiredbones.wikiarticles.entities

data class ArticleLocation(
    val id: String,
    val title: String,
    val lat: Double,
    val lon: Double,
    val dist: Double
)
