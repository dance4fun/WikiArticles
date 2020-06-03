package com.tiredbones.wikiarticles.feature.articles.cluster

import android.content.Context
import android.view.LayoutInflater
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.Cluster
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.view.DefaultClusterRenderer
import com.google.maps.android.ui.IconGenerator
import com.tiredbones.wikiarticles.R
import com.tiredbones.wikiarticles.databinding.ItemArticleClusterBinding

class ArticlesClusterRender(
    context: Context,
    map: GoogleMap,
    clusterManager: ClusterManager<ArticleClusterItem>
) : DefaultClusterRenderer<ArticleClusterItem>(context, map, clusterManager), OnClusterRenderListener {

  private val pinView by lazy {
    LayoutInflater.from(context).inflate(R.layout.item_article_pin, null) as ImageView
  }
  private val pinGenerator = IconGenerator(context).apply {
    setBackground(null)
    setContentView(pinView)
  }
  private val pinDefaultColor = ContextCompat.getColor(context, R.color.pin_default)
  private val pinSelectedColor = ContextCompat.getColor(context, R.color.pin_selected)

  @Suppress("RemoveExplicitTypeArguments")
  private val clusterBinding: ItemArticleClusterBinding by lazy {
    DataBindingUtil.inflate<ItemArticleClusterBinding>(
        LayoutInflater.from(context),
        R.layout.item_article_cluster,
        null,
        false
    )
  }
  private val clusterGenerator = IconGenerator(context).apply {
    setBackground(null)
    setContentView(clusterBinding.root)
  }

  private var selectedItem: ArticleClusterItem? = null

  override fun onBeforeClusterItemRendered(item: ArticleClusterItem, markerOptions: MarkerOptions) {
    updatePinView(selectedItem == item)
    markerOptions.icon(BitmapDescriptorFactory.fromBitmap(pinGenerator.makeIcon()))
  }

  override fun onBeforeClusterRendered(cluster: Cluster<ArticleClusterItem>, markerOptions: MarkerOptions) {
    updateClusterView(cluster)
    markerOptions.icon(BitmapDescriptorFactory.fromBitmap(clusterGenerator.makeIcon()))
  }

  override fun onClusterItemRendered(clusterItem: ArticleClusterItem, marker: Marker) {
    updatePinView(selectedItem == clusterItem)
    marker.setIcon(BitmapDescriptorFactory.fromBitmap(pinGenerator.makeIcon()))
  }

  override fun onClusterRendered(cluster: Cluster<ArticleClusterItem>, marker: Marker) {
    updateClusterView(cluster)
    marker.setIcon(BitmapDescriptorFactory.fromBitmap(clusterGenerator.makeIcon()))
  }

  override fun onItemSelected(item: ArticleClusterItem) {
    if (selectedItem != item) {
      onItemCleared()
    }
    selectedItem = item
    updatePinView(true)
    getMarker(item)?.setIcon(BitmapDescriptorFactory.fromBitmap(pinGenerator.makeIcon()))
  }

  override fun onItemCleared() {
    if (selectedItem != null) {
      updatePinView(false)
      getMarker(selectedItem)?.setIcon(BitmapDescriptorFactory.fromBitmap(pinGenerator.makeIcon()))
      selectedItem = null
    }
  }

  private fun updatePinView(isSelected: Boolean) {
    val pinColor = if (isSelected) pinSelectedColor else pinDefaultColor
    pinView.setColorFilter(pinColor)
  }

  private fun updateClusterView(cluster: Cluster<ArticleClusterItem>) {
    clusterBinding.clusterSize.text = cluster.items.size.run {
      if (this > CLUSTER_MAX_SIZE) CLUSTER_MAX_SIZE_LABEL else toString()
    }
  }

  companion object {
    private const val CLUSTER_MAX_SIZE = 99
    private const val CLUSTER_MAX_SIZE_LABEL = "99+"
  }
}

interface OnClusterRenderListener {

  fun onItemSelected(item: ArticleClusterItem)

  fun onItemCleared()
}
