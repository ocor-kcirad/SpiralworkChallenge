package com.hello.spiralworktask.view.login.createaccount

import android.arch.lifecycle.ViewModelProviders
import android.support.v4.app.FragmentActivity
import com.hello.spiralworktask.view.login.LoginActivity
import dagger.Module
import dagger.Provides

@Module
open class CreateAccountFragmentModule {

  @Provides
  fun provideViewModel(activity: LoginActivity): CreateAccountViewModel =
    ViewModelProviders.of(activity).get(CreateAccountViewModel::class.java)

}