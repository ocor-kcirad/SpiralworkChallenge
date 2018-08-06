package com.hello.spiralworktask.view.login

import com.hello.spiralworktask.libs.di.scopes.PerFragment
import com.hello.spiralworktask.view.login.emaillogin.EmailLoginFragment
import com.hello.spiralworktask.view.login.forgotpassword.ForgotPasswordFragment
import com.hello.spiralworktask.view.login.loginmain.LoginMainFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
internal abstract class LoginModule {

  @PerFragment
  @ContributesAndroidInjector()
  abstract fun bindEmailLoginFragment(): EmailLoginFragment

  @PerFragment
  @ContributesAndroidInjector()
  abstract fun bindForgotPasswordFragment(): ForgotPasswordFragment

  @PerFragment
  @ContributesAndroidInjector()
  abstract fun bindLoginMainFragment(): LoginMainFragment
}