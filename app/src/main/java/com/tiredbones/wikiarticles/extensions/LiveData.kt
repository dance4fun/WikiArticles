package com.tiredbones.wikiarticles.extensions

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

fun <T> LiveData<T>.subscribe(owner: LifecycleOwner, observer: (T) -> Unit) {
  observe(owner, Observer { item -> item?.let { observer(item) } })
}
