package com.tiredbones.wikiarticles.feature.articles

import com.google.android.gms.maps.model.LatLng
import com.tiredbones.wikiarticles.entities.ArticleDetails
import com.tiredbones.wikiarticles.entities.ArticleLocation
import com.tiredbones.wikiarticles.entities.GeoLocation
import com.tiredbones.wikiarticles.feature.articles.cluster.ArticleClusterItem
import javax.inject.Inject

class ArticlesMapper @Inject constructor() {

  fun mapToClusterItems(articleLocationData: List<ArticleLocation>) = articleLocationData.map(::ArticleClusterItem)

  fun mapToGmsModel(geoLocation: GeoLocation) = LatLng(geoLocation.lat, geoLocation.lon)

}
