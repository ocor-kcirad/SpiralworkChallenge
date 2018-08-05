package com.hello.spiralworktask.libs.di.module

import com.hello.spiralworktask.libs.di.scopes.PerActivity
import com.hello.spiralworktask.view.login.LoginActivity
import com.hello.spiralworktask.view.login.LoginFragmentsModule
import com.hello.spiralworktask.view.login.LoginModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
internal abstract class ActivityBindingModule {

  @PerActivity
  @ContributesAndroidInjector(modules = [LoginModule::class, LoginFragmentsModule::class])
  abstract fun bindLoginActivity(): LoginActivity

}
