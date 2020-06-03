package com.tiredbones.wikiarticles.feature.articles

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.location.LocationSettingsStatusCodes
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.Cluster
import com.google.maps.android.clustering.ClusterManager
import com.tiredbones.logger.Logger
import com.tiredbones.wikiarticles.base.BaseViewModel
import com.tiredbones.wikiarticles.entities.ArticleDetails
import com.tiredbones.wikiarticles.entities.GeoLocation
import com.tiredbones.wikiarticles.extensions.TAG
import com.tiredbones.wikiarticles.extensions.addTo
import com.tiredbones.wikiarticles.extensions.orZero
import com.tiredbones.wikiarticles.feature.articles.cluster.ArticleClusterItem
import com.tiredbones.wikiarticles.feature.location.GetRoutingPointsUseCase
import com.tiredbones.wikiarticles.feature.location.LocationProvider
import io.reactivex.Single
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ArticlesViewModel @Inject constructor(
    private val articlesMapper: ArticlesMapper,
    private val getArticlesDetailsUseCase: GetArticlesDetailsUseCase,
    private val getRoutingPointsUseCase: GetRoutingPointsUseCase,
    private val getNearbyArticlesUseCase: GetNearbyArticlesUseCase,
    private val locationProvider: LocationProvider
) : BaseViewModel(), GoogleMap.OnMapClickListener,
    ClusterManager.OnClusterClickListener<ArticleClusterItem>,
    ClusterManager.OnClusterItemClickListener<ArticleClusterItem> {

  val articlePins = MutableLiveData<List<ArticleClusterItem>>()
  val selectedPin = MutableLiveData<ArticleClusterItem>()
  val articleDetails = MutableLiveData<List<ArticleDetails>>()
  val isItemInfoVisible: LiveData<Boolean> = Transformations.map(articleDetails) { it.isNotEmpty() }
  val moveToPosition = MutableLiveData<LatLng>()
  val showRoute = MutableLiveData<List<LatLng>>()
  val openUrl = MutableLiveData<String>()
  val showLoading = MutableLiveData<Boolean>()
  val showLocationSettingsError = MutableLiveData<Unit>()
  private var currentLocation: GeoLocation? = null

  fun permissionGranted() {
    locationProvider.checkLocationSettings()
        .andThen(locationProvider.observeCurrentLocation())
        .subscribeOn(Schedulers.io())
        .observeOn(Schedulers.io())
        .doOnNext { currentLocation = it }
        .switchMapSingle(getNearbyArticlesUseCase::invoke)
        .map(articlesMapper::mapToClusterItems)
        .subscribe(articlePins::postValue) {
          if ((it is ApiException) && it.statusCode == LocationSettingsStatusCodes.RESOLUTION_REQUIRED) {
            showLocationSettingsError.postValue(Unit)
          } else {
            Logger.e(TAG, it)
          }
        }
        .addTo(disposables)
  }

  override fun onMapClick(latLng: LatLng?) {
    articleDetails.value = emptyList()
  }

  override fun onClusterClick(cluster: Cluster<ArticleClusterItem>): Boolean {
    val items = cluster.items
    if (items.isEmpty()) return false
    loadDetails(items.map { it.article.id })
    moveToPosition.value = items.first().position
    return true
  }

  override fun onClusterItemClick(item: ArticleClusterItem): Boolean {
    selectedPin.value = item
    loadDetails(listOf(item.article.id))
    moveToPosition.value = item.position
    return true
  }

  fun onCurrentLocationButtonClicked() {
    currentLocation?.let {
      moveToPosition.postValue(articlesMapper.mapToGmsModel(it))
    }
  }

  fun onShowArticleClicked(selectedIndex: Int) {
    Single.fromCallable { articleDetails.value?.get(selectedIndex)?.url.orEmpty() }
        .subscribeOn(Schedulers.io())
        .subscribe(Consumer {
          it.takeIf { !it.isNullOrBlank() }.let(openUrl::postValue)
        })
        .addTo(disposables)
  }

  fun onShowRouteClicked(selectedIndex: Int) {
    Single.fromCallable {
      val selectedArticleId = articleDetails.value?.get(selectedIndex)?.id
      val destination = articlePins.value?.find { it.article.id == selectedArticleId }?.article.let {
        GeoLocation(it?.lat.orZero(), it?.lon.orZero())
      }
      currentLocation!! to destination
    }
        .flatMap { (from, to) -> getRoutingPointsUseCase(from, to) }
        .subscribeOn(Schedulers.io())
        .map { it.map(articlesMapper::mapToGmsModel) }
        .subscribe(Consumer(showRoute::postValue))
        .addTo(disposables)
  }

  private fun loadDetails(pageIds: List<String>) {
    getArticlesDetailsUseCase(pageIds)
        .doOnSubscribe { showLoading.postValue(true) }
        .doOnSuccess { showLoading.postValue(false) }
        .subscribeOn(Schedulers.io())
        .subscribe(articleDetails::postValue) {
          Logger.e(TAG, it, "Could not load item details.")
        }
        .addTo(disposables)
  }
}
