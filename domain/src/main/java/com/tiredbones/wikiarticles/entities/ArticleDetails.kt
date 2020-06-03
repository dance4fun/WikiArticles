package com.tiredbones.wikiarticles.entities

data class ArticleDetails(
    val id: String,
    val title: String,
    val description: String,
    val url: String,
    val image: String?
)
