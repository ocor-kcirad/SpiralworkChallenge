package com.hello.spiralworktask.libs.arch

import android.arch.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.internal.disposables.DisposableContainer

open class BaseViewModel : ViewModel() {

  private val compositeDisposable = CompositeDisposable()

  protected val disposableContainer: DisposableContainer get() = compositeDisposable

  override fun onCleared() {
    compositeDisposable.dispose()
    super.onCleared()
  }

}