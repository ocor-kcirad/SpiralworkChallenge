package com.hello.spiralworktask.view.login

import com.hello.spiralworktask.libs.di.scopes.PerFragment
import com.hello.spiralworktask.view.login.emaillogin.EmailLoginFragment
import com.hello.spiralworktask.view.login.emaillogin.EmailLoginFragmentModule
import com.hello.spiralworktask.view.login.forgotpassword.ForgotPasswordFragment
import com.hello.spiralworktask.view.login.forgotpassword.ForgotPasswordFragmentModule
import com.hello.spiralworktask.view.login.loginmain.LoginMainFragment
import com.hello.spiralworktask.view.login.loginmain.LoginMainFragmentModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
internal abstract class LoginFragmentsModule {

  @PerFragment
  @ContributesAndroidInjector(modules = [(EmailLoginFragmentModule::class)])
  abstract fun bindEmailLoginFragment(): EmailLoginFragment

  @PerFragment
  @ContributesAndroidInjector(modules = [(ForgotPasswordFragmentModule::class)])
  abstract fun bindForgotPasswordFragment(): ForgotPasswordFragment

  @PerFragment
  @ContributesAndroidInjector(modules = [(LoginMainFragmentModule::class)])
  abstract fun bindLoginMainFragment(): LoginMainFragment
}