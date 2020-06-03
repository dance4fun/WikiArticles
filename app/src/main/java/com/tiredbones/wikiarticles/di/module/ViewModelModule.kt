package com.tiredbones.wikiarticles.di.module

import androidx.lifecycle.ViewModelProvider
import com.tiredbones.wikiarticles.base.BaseViewModel
import com.tiredbones.wikiarticles.di.ViewModelFactory
import com.tiredbones.wikiarticles.di.ViewModelKey
import com.tiredbones.wikiarticles.feature.articles.ArticlesViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
abstract class ViewModelModule {

  @Binds
  @Singleton
  internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

  @Binds
  @IntoMap
  @ViewModelKey(ArticlesViewModel::class)
  internal abstract fun bindArticlesViewModel(viewModel: ArticlesViewModel): BaseViewModel

}
