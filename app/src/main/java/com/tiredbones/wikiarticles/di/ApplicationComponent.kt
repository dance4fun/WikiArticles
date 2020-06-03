package com.tiredbones.wikiarticles.di

import android.content.Context
import com.tiredbones.wikiarticles.WikiApplication
import com.tiredbones.wikiarticles.base.BaseActivity
import com.tiredbones.wikiarticles.di.module.LocationServicesModule
import com.tiredbones.wikiarticles.di.module.NetworkModule
import com.tiredbones.wikiarticles.di.module.RepositoryModule
import com.tiredbones.wikiarticles.di.module.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
      ViewModelModule::class,
      RepositoryModule::class,
      LocationServicesModule::class,
      NetworkModule::class
    ]
)
interface ApplicationComponent {

  fun inject(app: BaseActivity)

  @Component.Builder
  interface Builder {
    @BindsInstance
    fun application(application: WikiApplication): Builder

    fun build(): ApplicationComponent
  }
}
