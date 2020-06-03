package com.tiredbones.wikiarticles.di.module

import com.tiredbones.wikiarticles.feature.articles.ArticlesRemoteRepository
import com.tiredbones.wikiarticles.feature.articles.ArticlesRepository
import dagger.Binds
import dagger.Module

@Module
abstract class RepositoryModule {

  @Binds
  internal abstract fun bindRepository(repository: ArticlesRemoteRepository): ArticlesRepository

}
