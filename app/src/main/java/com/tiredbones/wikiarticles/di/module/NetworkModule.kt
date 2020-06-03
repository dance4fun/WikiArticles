package com.tiredbones.wikiarticles.di.module

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.tiredbones.wikiarticles.BuildConfig
import com.tiredbones.wikiarticles.feature.articles.ArticlesApi
import com.tiredbones.wikiarticles.network.interceptor.ApiParametersInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

  @Provides
  @Singleton
  internal fun provideHttpClient(): OkHttpClient {
    val builder = OkHttpClient.Builder()
    builder.addInterceptor(ApiParametersInterceptor())

    if (BuildConfig.DEBUG) {
      builder.addInterceptor(HttpLoggingInterceptor())
          .addNetworkInterceptor(StethoInterceptor())
    }
    return builder.build()
  }

  @Provides
  @Singleton
  internal fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
      Retrofit.Builder()
          .baseUrl(BuildConfig.BASE_URL)
          .client(okHttpClient)
          .addConverterFactory(GsonConverterFactory.create())
          .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
          .build()

  @Provides
  internal fun provideArticlesApi(retrofit: Retrofit): ArticlesApi = retrofit.create(
      ArticlesApi::class.java)
}
