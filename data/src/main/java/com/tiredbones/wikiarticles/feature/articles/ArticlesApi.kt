package com.tiredbones.wikiarticles.feature.articles

import com.tiredbones.wikiarticles.entities.GeoLocation
import com.tiredbones.wikiarticles.network.response.ArticlesDetailsResponse
import com.tiredbones.wikiarticles.network.response.NearbyArticlesResponse
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ArticlesApi {

  @GET("w/api.php")
  fun getNearbyArticles(
      @Query("gscoord") gscoord: GeoLocation,
      @Query("action") action: String = PARAM_ACTION,
      @Query("list") list: String = PARAM_LIST,
      @Query("gsradius") gsradius: Int = PARAM_RADIUS,
      @Query("gslimit") gslimit: Int = PARAM_LIMIT
  ): Single<NearbyArticlesResponse>

  @GET("w/api.php")
  fun getArticlesDetails(
      @Query("pageids") pageids: String,
      @Query("action") action: String = PARAM_ACTION,
      @Query("prop") prop: String = PARAM_DETAILS,
      @Query("inprop") inprop: String = PARAM_URL,
      @Query("piprop") piprop: String = PAGE_IMAGE_PROP
  ): Single<ArticlesDetailsResponse>

  private companion object {
    const val PARAM_ACTION = "query"
    const val PARAM_DETAILS = "info|description|pageimages"
    const val PARAM_LIST = "geosearch"
    const val PARAM_URL = "url"
    const val PAGE_IMAGE_PROP = "original"
    const val PARAM_RADIUS = 10000
    const val PARAM_LIMIT = 50
  }
}
