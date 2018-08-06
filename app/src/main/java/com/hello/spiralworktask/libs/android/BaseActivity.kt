package com.hello.spiralworktask.libs.android

import dagger.android.support.DaggerAppCompatActivity
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.internal.disposables.DisposableContainer

open class BaseActivity : DaggerAppCompatActivity() {

  private val compositeDisposable = CompositeDisposable()

  protected val disposableContainer: DisposableContainer get() = compositeDisposable

  override fun onDestroy() {
    super.onDestroy()
    compositeDisposable.dispose()
  }

}