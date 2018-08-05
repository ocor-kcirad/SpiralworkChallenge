package com.hello.spiralworktask.view.login.loginmain

import android.arch.lifecycle.ViewModelProviders
import com.hello.spiralworktask.view.login.LoginActivity
import dagger.Module
import dagger.Provides

@Module
open class LoginMainFragmentModule {

  @Provides
  fun provideViewModel(activity: LoginActivity): LoginMainViewModel =
    ViewModelProviders.of(activity).get(LoginMainViewModel::class.java)

}