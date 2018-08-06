package com.hello.spiralworktask.app

import com.hello.spiralworktask.BuildConfig.URL_SERVER
import com.hello.spiralworktask.libs.android.BaseApplication
import com.hello.spiralworktask.libs.di.module.NetworkModule

class App : BaseApplication() {

  override fun applicationInjector() = DaggerAppComponent.builder()
      .appModule(AppModule(this))
      .networkModule(NetworkModule(URL_SERVER))
      .build()

}