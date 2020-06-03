package com.tiredbones.wikiarticles.feature.articles

import com.tiredbones.wikiarticles.entities.ArticleDetails
import com.tiredbones.wikiarticles.entities.GeoLocation
import com.tiredbones.wikiarticles.entities.ArticleLocation
import io.reactivex.Single

interface ArticlesRepository {

  fun getNearbyArticles(geoLocation: GeoLocation): Single<List<ArticleLocation>>

  fun getArticlesDetails(pageIds: List<String>): Single<List<ArticleDetails>>
}
