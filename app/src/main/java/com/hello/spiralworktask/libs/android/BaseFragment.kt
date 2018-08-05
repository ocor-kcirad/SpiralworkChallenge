package com.hello.spiralworktask.libs.android

import android.support.v4.app.Fragment
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.internal.disposables.DisposableContainer

abstract class BaseFragment : Fragment() {

  private val compositeDisposable = CompositeDisposable()

  protected val disposableContainer: DisposableContainer get() = compositeDisposable

  override fun onDestroy() {
    super.onDestroy()
    compositeDisposable.dispose()
  }
}