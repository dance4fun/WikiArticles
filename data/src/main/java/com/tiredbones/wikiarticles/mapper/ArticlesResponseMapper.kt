package com.tiredbones.wikiarticles.mapper

import com.tiredbones.wikiarticles.entities.ArticleDetails
import com.tiredbones.wikiarticles.entities.ArticleLocation
import com.tiredbones.wikiarticles.network.response.ArticlesDetailsResponse
import com.tiredbones.wikiarticles.network.response.NearbyArticlesResponse

fun NearbyArticlesResponse.map() = query.geosearch.map {
  ArticleLocation(
      id = it.pageid,
      title = it.title,
      lat = it.lat,
      lon = it.lon,
      dist = it.dist
  )
}

fun ArticlesDetailsResponse.map() = query.pages.values.map {
  ArticleDetails(
      id = it.pageid,
      title = it.title,
      description = it.description.orEmpty(),
      url = it.fullurl,
      image = it.original?.source
  )
}
