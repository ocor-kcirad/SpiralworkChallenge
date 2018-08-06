package com.hello.spiralworktask.libs.ext

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.support.v4.app.Fragment

inline fun <reified T : ViewModel> Fragment.getViewModel(viewModelFactory: ViewModelProvider.Factory): T {
  return ViewModelProviders.of(this, viewModelFactory)[T::class.java]
}

inline fun <reified T : ViewModel> Fragment.withViewModel(
  viewModelFactory: ViewModelProvider.Factory,
  body: T.() -> Unit
): T {
  val vm = getViewModel<T>(viewModelFactory)
  vm.body()
  return vm
}