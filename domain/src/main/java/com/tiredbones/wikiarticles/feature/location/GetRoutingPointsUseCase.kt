package com.tiredbones.wikiarticles.feature.location

import com.tiredbones.wikiarticles.entities.GeoLocation
import io.reactivex.Single
import javax.inject.Inject
import kotlin.math.max
import kotlin.math.min

class GetRoutingPointsUseCase @Inject constructor() {

  operator fun invoke(fromLocation: GeoLocation, toLocation: GeoLocation): Single<List<GeoLocation>> =
      Single.fromCallable { getDirectionPoints(fromLocation, toLocation) }

  // pretend we are using Directions SDK as it is billable
  private fun getDirectionPoints(from: GeoLocation, to: GeoLocation): List<GeoLocation> =
      mutableListOf<GeoLocation>().apply {
        add(from)
        val minLat = min(from.lat, to.lat)
        val maxLat = max(from.lat, to.lat)
        val minLon = min(from.lon, to.lon)
        val maxLon = max(from.lon, to.lon)
        add(GeoLocation(getRandomNumber(minLat, maxLat), getRandomNumber(minLon, maxLon)))
        add(GeoLocation(getRandomNumber(minLat, maxLat), getRandomNumber(minLon, maxLon)))
        add(to)
      }

  private fun getRandomNumber(min: Double, max: Double) =
      (((min * 100).toInt()..(max * 100).toInt()).random()).toDouble() / 100
}
