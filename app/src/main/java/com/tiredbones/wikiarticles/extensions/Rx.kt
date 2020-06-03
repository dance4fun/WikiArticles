package com.tiredbones.wikiarticles.extensions

import io.reactivex.Observable
import io.reactivex.ObservableOperator
import io.reactivex.Observer
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Add the [Disposable] to a [CompositeDisposable].
 * @param compositeDisposable [CompositeDisposable] to add this disposable to
 * @return this instance
 */
fun Disposable.addTo(compositeDisposable: CompositeDisposable): Disposable =
    apply { compositeDisposable.add(this) }

/**
 * This operator emits items periodically. All items received in time before periods passed are skipped.
 * @param periodMillis the time to skip in Millis
 */
fun <T> Observable<T>.skipPeriod(periodMillis: Long): Observable<T> = lift(SkipPeriodOperator(periodMillis))

private class SkipPeriodObserver<T>(
    private val downstream: Observer<T>,
    private val period: Long
) : Observer<T>, Disposable {

  private var upstream: Disposable? = null
  private var lastEmittedTime: Long = 0

  override fun onSubscribe(d: Disposable) {
    if (upstream != null) {
      d.dispose()
    } else {
      upstream = d
      downstream.onSubscribe(this)
    }
  }

  override fun onNext(item: T) {
    val currentTimeStamp = System.currentTimeMillis()
    val delta = currentTimeStamp - lastEmittedTime
    // skip all items that come before period is passed
    if (delta > period) {
      lastEmittedTime = System.currentTimeMillis()
      downstream.onNext(item)
    }
  }

  override fun onError(throwable: Throwable) {
    downstream.onError(throwable)
  }

  override fun onComplete() {
    downstream.onComplete()
  }

  override fun dispose() {
    upstream?.dispose()
  }

  override fun isDisposed(): Boolean {
    return upstream?.isDisposed ?: true
  }
}

private class SkipPeriodOperator<T>(private val periodMillis: Long) : ObservableOperator<T, T> {

  override fun apply(observer: Observer<in T>): Observer<in T> {
    return SkipPeriodObserver(observer, periodMillis)
  }
}
