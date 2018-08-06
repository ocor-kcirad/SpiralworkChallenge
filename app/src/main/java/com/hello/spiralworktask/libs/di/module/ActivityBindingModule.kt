package com.hello.spiralworktask.libs.di.module

import com.hello.spiralworktask.libs.di.scopes.PerActivity
import com.hello.spiralworktask.view.login.LoginActivity
import com.hello.spiralworktask.view.login.LoginModule
import com.hello.spiralworktask.view.register.RegisterActivity
import com.hello.spiralworktask.view.register.RegisterActivityModule
import com.hello.spiralworktask.view.welcome.WelcomeUserActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
internal abstract class ActivityBindingModule {

  @PerActivity
  @ContributesAndroidInjector(modules = [LoginModule::class])
  abstract fun bindLoginActivity(): LoginActivity

  @PerActivity
  @ContributesAndroidInjector(modules = [RegisterActivityModule::class])
  abstract fun bindRegisterActivity(): RegisterActivity

  @PerActivity
  @ContributesAndroidInjector()
  abstract fun bindWelcomeUserActivity(): WelcomeUserActivity

}
