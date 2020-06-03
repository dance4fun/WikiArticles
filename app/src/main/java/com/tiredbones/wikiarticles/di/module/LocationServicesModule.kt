package com.tiredbones.wikiarticles.di.module

import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.SettingsClient
import com.tiredbones.wikiarticles.WikiApplication
import com.tiredbones.wikiarticles.feature.location.GpsLocationProvider
import com.tiredbones.wikiarticles.feature.location.LocationProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class LocationServicesModule {

  @Provides
  internal fun provideFusedLocationProviderClient(context: WikiApplication): FusedLocationProviderClient =
      LocationServices.getFusedLocationProviderClient(context)

  @Provides
  internal fun provideSettingsClient(context: WikiApplication): SettingsClient =
      LocationServices.getSettingsClient(context)

  @Provides
  @Singleton
  internal fun provideLocationProvider(
      locationClient: FusedLocationProviderClient,
      settingsClient: SettingsClient
  ): LocationProvider = GpsLocationProvider(settingsClient, locationClient)

}
