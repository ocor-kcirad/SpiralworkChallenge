package com.hello.spiralworktask.view.login.emaillogin

import android.arch.lifecycle.ViewModelProviders
import com.hello.spiralworktask.view.login.LoginActivity
import dagger.Module
import dagger.Provides

@Module
open class EmailLoginFragmentModule {

  @Provides
  fun provideViewModel(activity: LoginActivity): EmailLoginViewModel =
    ViewModelProviders.of(activity).get(EmailLoginViewModel::class.java)

}