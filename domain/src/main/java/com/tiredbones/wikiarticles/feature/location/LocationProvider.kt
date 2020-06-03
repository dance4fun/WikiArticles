package com.tiredbones.wikiarticles.feature.location

import com.tiredbones.wikiarticles.entities.GeoLocation
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

interface LocationProvider {

  fun observeCurrentLocation(): Observable<GeoLocation>

  fun checkLocationSettings(): Completable
}
