package com.tiredbones.wikiarticles.feature.articles

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.mock
import com.tiredbones.wikiarticles.entities.ArticleLocation
import com.tiredbones.wikiarticles.entities.GeoLocation
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
internal class GetNearbyArticlesUseCaseTest {
  @Mock lateinit var articlesRepository: ArticlesRepository

  private lateinit var sut: GetNearbyArticlesUseCase

  @Before
  fun setUp() {
    sut = GetNearbyArticlesUseCase(articlesRepository)
  }

  @Test
  fun `invoke should returns list with article locations from repository`() {
    val geoLocation = GeoLocation(10.0, 20.3)
    val result = listOf(ArticleLocation(id = "1", title = "2", lat = 3.0, lon = 4.0, dist = 5.0))
    given(articlesRepository.getNearbyArticles(geoLocation)).willReturn(
        Single.just(result)
    )

    sut.invoke(geoLocation).test().assertValue(result)
  }

  @Test
  fun `invoke should return an error if repository returns error`() {
    val error = mock<Exception>()
    given(articlesRepository.getNearbyArticles(any())).willReturn(
        Single.error(error)
    )

    sut.invoke(GeoLocation(10.0, 20.3)).test().assertError(error)
  }
}
