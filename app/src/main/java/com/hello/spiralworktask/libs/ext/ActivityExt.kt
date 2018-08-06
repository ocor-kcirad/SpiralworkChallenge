package com.hello.spiralworktask.libs.ext

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity

fun AppCompatActivity.replaceFragment(
  frameId: Int,
  fragment: Fragment
) {
  supportFragmentManager.inTransaction { replace(frameId, fragment) }
}

inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
  beginTransaction().func()
      .commit()
}

inline fun <reified T : ViewModel> FragmentActivity.getViewModel(viewModelFactory: ViewModelProvider.Factory): T {
  return ViewModelProviders.of(this, viewModelFactory)[T::class.java]
}

inline fun <reified T : ViewModel> FragmentActivity.withViewModel(
  viewModelFactory: ViewModelProvider.Factory,
  body: T.() -> Unit
): T {
  val vm = getViewModel<T>(viewModelFactory)
  vm.body()
  return vm
}

fun <T : Any, L : LiveData<T>> LifecycleOwner.observe(
  liveData: L,
  body: (T?) -> Unit
) {
  liveData.observe(this, Observer(body))
}
