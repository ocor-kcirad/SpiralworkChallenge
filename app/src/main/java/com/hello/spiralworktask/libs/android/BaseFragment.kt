package com.hello.spiralworktask.libs.android

import dagger.android.support.DaggerFragment
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.internal.disposables.DisposableContainer

abstract class BaseFragment : DaggerFragment() {

  private val compositeDisposable = CompositeDisposable()

  protected val disposableContainer: DisposableContainer get() = compositeDisposable

  override fun onDestroy() {
    super.onDestroy()
    compositeDisposable.dispose()
  }
}