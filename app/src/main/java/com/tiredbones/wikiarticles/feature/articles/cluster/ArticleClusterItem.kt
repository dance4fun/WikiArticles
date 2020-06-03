package com.tiredbones.wikiarticles.feature.articles.cluster

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem
import com.tiredbones.wikiarticles.entities.ArticleLocation

data class ArticleClusterItem(val article: ArticleLocation) : ClusterItem {

    override fun getSnippet(): String = article.id

    override fun getTitle(): String = article.title

    override fun getPosition(): LatLng = article.run { LatLng(lat, lon) }
}
