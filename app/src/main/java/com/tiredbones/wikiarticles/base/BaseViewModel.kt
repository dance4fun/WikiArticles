package com.tiredbones.wikiarticles.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel : ViewModel() {

  protected val disposables = CompositeDisposable()

  override fun onCleared() {
    disposables.dispose()
  }
}
