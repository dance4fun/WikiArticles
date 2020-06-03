package com.tiredbones.wikiarticles.extensions

val Any.TAG: String
  get() = this::class.java.simpleName

fun Double?.orZero() = this ?: 0.0
