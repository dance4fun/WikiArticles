package com.tiredbones.wikiarticles.feature.articles

import com.tiredbones.wikiarticles.entities.ArticleDetails
import com.tiredbones.wikiarticles.entities.ArticleLocation
import com.tiredbones.wikiarticles.entities.GeoLocation
import io.reactivex.Single
import javax.inject.Inject

class GetArticlesDetailsUseCase @Inject constructor(
    private val repository: ArticlesRepository
) {

  operator fun invoke(pageIds: List<String>): Single<List<ArticleDetails>> = repository.getArticlesDetails(pageIds)
}
