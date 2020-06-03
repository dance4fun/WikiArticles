package com.tiredbones.wikiarticles.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tiredbones.wikiarticles.base.BaseViewModel
import javax.inject.Inject
import javax.inject.Provider

class ViewModelFactory @Inject constructor(
    private val viewModels: MutableMap<Class<out ViewModel>, Provider<BaseViewModel>>
) : ViewModelProvider.Factory {

  @Suppress("UNCHECKED_CAST")
  override fun <T : ViewModel> create(modelClass: Class<T>): T {
    var provider = viewModels[modelClass]
    provider = provider ?: viewModels.entries.firstOrNull { modelClass.isAssignableFrom(it.key) }?.value
    checkNotNull(provider) { "unknown view model class: $modelClass" }
    return provider.get() as T
  }
}
