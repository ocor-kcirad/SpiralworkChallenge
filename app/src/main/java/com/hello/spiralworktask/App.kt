package com.hello.spiralworktask

import com.hello.spiralworktask.libs.android.BaseApplication

class App : BaseApplication() {

  override fun applicationInjector() = DaggerAppComponent.builder().create(this)

}