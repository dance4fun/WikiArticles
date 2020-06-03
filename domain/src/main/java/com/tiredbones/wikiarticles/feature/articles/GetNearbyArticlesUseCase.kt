package com.tiredbones.wikiarticles.feature.articles

import com.tiredbones.wikiarticles.entities.ArticleLocation
import com.tiredbones.wikiarticles.entities.GeoLocation
import io.reactivex.Single
import javax.inject.Inject

class GetNearbyArticlesUseCase @Inject constructor(
    private val repository: ArticlesRepository
) {

  operator fun invoke(geoLocation: GeoLocation): Single<List<ArticleLocation>> = repository.getNearbyArticles(geoLocation)
}
