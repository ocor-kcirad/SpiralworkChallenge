package com.hello.spiralworktask.view.login.forgotpassword

import android.arch.lifecycle.ViewModelProviders
import com.hello.spiralworktask.view.login.LoginActivity
import dagger.Module
import dagger.Provides

@Module
open class ForgotPasswordFragmentModule {

  @Provides
  fun provideViewModel(activity: LoginActivity): ForgotPasswordViewModel =
    ViewModelProviders.of(activity).get(ForgotPasswordViewModel::class.java)

}