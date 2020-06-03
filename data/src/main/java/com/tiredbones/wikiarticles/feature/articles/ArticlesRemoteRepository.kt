package com.tiredbones.wikiarticles.feature.articles

import com.tiredbones.wikiarticles.entities.ArticleDetails
import com.tiredbones.wikiarticles.entities.ArticleLocation
import com.tiredbones.wikiarticles.entities.GeoLocation
import com.tiredbones.wikiarticles.mapper.map
import com.tiredbones.wikiarticles.network.response.ArticlesDetailsResponse
import com.tiredbones.wikiarticles.network.response.NearbyArticlesResponse
import io.reactivex.Single
import javax.inject.Inject

class ArticlesRemoteRepository @Inject constructor(
    private val articlesApi: ArticlesApi
) : ArticlesRepository {

  override fun getNearbyArticles(geoLocation: GeoLocation): Single<List<ArticleLocation>> =
      articlesApi.getNearbyArticles(geoLocation).map(NearbyArticlesResponse::map)

  override fun getArticlesDetails(pageIds: List<String>): Single<List<ArticleDetails>> =
      articlesApi.getArticlesDetails(pageIds.joinToString(QUERY_SEPARATOR)).map(ArticlesDetailsResponse::map)

  companion object {
    const val QUERY_SEPARATOR = "|"
  }
}
