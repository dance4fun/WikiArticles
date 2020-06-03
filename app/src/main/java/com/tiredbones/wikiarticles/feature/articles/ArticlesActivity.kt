package com.tiredbones.wikiarticles.feature.articles

import android.Manifest
import android.graphics.Color
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.Polyline
import com.google.android.gms.maps.model.PolylineOptions
import com.google.maps.android.clustering.ClusterManager
import com.tiredbones.wikiarticles.R
import com.tiredbones.wikiarticles.base.BaseActivity
import com.tiredbones.wikiarticles.databinding.ActivityArticlesBinding
import com.tiredbones.wikiarticles.extensions.getViewModel
import com.tiredbones.wikiarticles.extensions.openUrl
import com.tiredbones.wikiarticles.extensions.subscribe
import com.tiredbones.wikiarticles.feature.articles.cluster.ArticleClusterItem
import com.tiredbones.wikiarticles.feature.articles.cluster.ArticlesClusterRender
import com.tiredbones.wikiarticles.feature.articles.cluster.OnClusterRenderListener
import com.tiredbones.wikiarticles.feature.articles.info.ArticlesInfoAdapter
import com.tiredbones.wikiarticles.util.setVisibility
import permissions.dispatcher.NeedsPermission
import permissions.dispatcher.OnPermissionDenied
import permissions.dispatcher.RuntimePermissions

@RuntimePermissions
@Suppress("RemoveExplicitTypeArguments")
class ArticlesActivity : BaseActivity() {

  private var clusterManager: ClusterManager<ArticleClusterItem>? = null
  private val viewModel: ArticlesViewModel by lazy {
    getViewModel<ArticlesViewModel>(viewModelFactory)
  }
  private val binding: ActivityArticlesBinding by lazy {
    DataBindingUtil.setContentView<ActivityArticlesBinding>(this, R.layout.activity_articles)
  }

  private val mapView: MapView
    get() = binding.map

  private lateinit var googleMap: GoogleMap
  private var route: Polyline? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding.lifecycleOwner = this
    mapView.onCreate(savedInstanceState)

    binding.fab.setOnClickListener { viewModel.onCurrentLocationButtonClicked() }
    binding.showArticleButton.setOnClickListener {
      viewModel.onShowArticleClicked(binding.infoPager.currentItem)
    }
    binding.routeButton.setOnClickListener {
      viewModel.onShowRouteClicked(binding.infoPager.currentItem)
    }

    viewModel.openUrl.subscribe(this, ::openUrl)
    viewModel.showLocationSettingsError.subscribe(this, {
      showError(R.string.locations_settings_error)
    })
    viewModel.showLoading.subscribe(this, {
      if (it) binding.itemCardView.setVisibility(true)
      binding.loadingView.setVisibility(it)
    })
    viewModel.articlePins.subscribe(this, ::updateMarkers)
    viewModel.moveToPosition.subscribe(this, {
      googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(it, DEFAULT_ZOOM))
    })
    viewModel.showRoute.subscribe(this, {
      route?.remove()
      route = googleMap.addPolyline(
          PolylineOptions()
              .addAll(it)
              .color(Color.DKGRAY)
      )
    })
    viewModel.selectedPin.observe(this, Observer(::updateClusterRender))

    setupInfoPager()
  }

  @NeedsPermission(Manifest.permission.ACCESS_FINE_LOCATION)
  fun requestLocationSuccess() {
    viewModel.permissionGranted()
    mapView.getMapAsync {
      it.isMyLocationEnabled = true
      it.uiSettings.isMyLocationButtonEnabled = false
      setupClusterManager(it)
      googleMap = it
    }
    binding.fab.setVisibility(true)
  }

  @OnPermissionDenied(Manifest.permission.ACCESS_FINE_LOCATION)
  fun requestLocationDenied() {
    showError(R.string.permission_denied_error)
    binding.fab.setVisibility(false)
  }

  override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    onRequestPermissionsResult(requestCode, grantResults)
  }

  private fun setupClusterManager(googleMap: GoogleMap) {
    val clusterManager = ClusterManager<ArticleClusterItem>(this, googleMap)

    val merchantsClusterRender = ArticlesClusterRender(this, googleMap, clusterManager)
    viewModel.selectedPin.value?.let(::updateClusterRender)

    val onCameraIdleListener = GoogleMap.OnCameraIdleListener { clusterManager.onCameraIdle() }

    with(googleMap) {
      setOnCameraIdleListener(onCameraIdleListener)
      setOnMarkerClickListener(clusterManager)
      setOnInfoWindowClickListener(clusterManager)
      setOnMapClickListener(viewModel)
    }

    with(clusterManager) {
      renderer = merchantsClusterRender
      setOnClusterClickListener(viewModel)
      setOnClusterItemClickListener(viewModel)
    }
    this.clusterManager = clusterManager
  }

  private fun updateMarkers(items: List<ArticleClusterItem>) {
    clusterManager?.run {
      clearItems()
      addItems(items)
      cluster()
    }
  }

  private fun updateClusterRender(item: ArticleClusterItem?) {
    (clusterManager?.renderer as? OnClusterRenderListener)?.let {
      if (item == null) {
        it.onItemCleared()
      } else {
        it.onItemSelected(item)
      }
    }
  }

  private fun setupInfoPager() {
    val adapter = ArticlesInfoAdapter()
    binding.infoPager.adapter = adapter
    viewModel.articleDetails.subscribe(this, {
      adapter.updateList(it)
      binding.helperArrows.setVisibility(it.size > 1)
    })
    viewModel.isItemInfoVisible.subscribe(this, {
      binding.itemCardView.setVisibility(it)
    })
  }

  override fun onStart() {
    super.onStart()
    mapView.onStart()
    requestLocationSuccessWithPermissionCheck()
  }

  override fun onResume() {
    super.onResume()
    mapView.onResume()
  }

  override fun onPause() {
    super.onPause()
    mapView.onPause()
  }

  override fun onStop() {
    super.onStop()
    mapView.onStop()
  }

  override fun onLowMemory() {
    super.onLowMemory()
    mapView.onLowMemory()
  }

  companion object {
    const val DEFAULT_ZOOM = 14f
  }
}
