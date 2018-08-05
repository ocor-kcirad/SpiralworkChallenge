package com.hello.spiralworktask

import com.hello.spiralworktask.libs.di.module.ActivityBindingModule
import com.hello.spiralworktask.libs.di.module.NetworkModule
import com.hello.spiralworktask.libs.di.module.ViewModelModule
import com.hello.spiralworktask.libs.di.scopes.PerApplication
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

@Component(
    modules = [
      (AppModule::class),
      (NetworkModule::class),
      (ActivityBindingModule::class),
      (AndroidInjectionModule::class),
      (ViewModelModule::class),
      (AndroidSupportInjectionModule::class)]
)
@PerApplication
interface AppComponent : AndroidInjector<App> {

  @Component.Builder
  abstract class Builder : AndroidInjector.Builder<App>()
}