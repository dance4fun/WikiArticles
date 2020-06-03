package com.tiredbones.wikiarticles.feature.location

import android.os.Looper
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.SettingsClient
import com.tiredbones.logger.Logger
import com.tiredbones.wikiarticles.entities.GeoLocation
import com.tiredbones.wikiarticles.extensions.TAG
import com.tiredbones.wikiarticles.extensions.skipPeriod
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import java.util.concurrent.TimeUnit

class GpsLocationProvider constructor(
    private val settingsClient: SettingsClient,
    private val locationClient: FusedLocationProviderClient
) : LocationProvider {

  private val locationStream = BehaviorSubject.create<GeoLocation>()

  private val locationRequest = LocationRequest().apply {
    interval = TimeUnit.MINUTES.toMillis(UPDATE_INTERVAL_IN_MINUTES)
    fastestInterval = TimeUnit.MINUTES.toMillis(FASTEST_UPDATE_INTERVAL_IN_MINUTES)
    priority = LocationRequest.PRIORITY_HIGH_ACCURACY
  }

  private val locationCallback = object : LocationCallback() {
    override fun onLocationResult(result: LocationResult?) {
      result?.let {
        val location = it.lastLocation
        locationStream.onNext(GeoLocation(location.latitude, location.longitude))
      }
    }
  }

  // Using skipPeriod because requestLocationUpdates emits items too often and ignores interval that was set
  // https://stackoverflow.com/questions/3449676/locationmanager-calling-onlocationchanged-too-often
  override fun observeCurrentLocation(): Observable<GeoLocation> {
    locationClient.removeLocationUpdates(locationCallback)
    locationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
        .addOnFailureListener { locationStream.onError(it) }
    return locationStream.skipPeriod(TimeUnit.MINUTES.toMillis(UPDATE_INTERVAL_IN_MINUTES)).hide()
  }

  override fun checkLocationSettings(): Completable = Completable.fromAction {
    val request = LocationSettingsRequest.Builder()
        .addLocationRequest(locationRequest)
        .build()

    settingsClient.checkLocationSettings(request)
        .addOnSuccessListener {
          Logger.d(TAG, "Location settings are enabled.")
        }
  }

  companion object {
    private const val UPDATE_INTERVAL_IN_MINUTES = 1L
    private const val FASTEST_UPDATE_INTERVAL_IN_MINUTES = UPDATE_INTERVAL_IN_MINUTES / 2L
  }
}
