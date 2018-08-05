package com.hello.spiralworktask.view.login

import android.arch.lifecycle.ViewModel
import com.hello.spiralworktask.libs.di.scopes.PerFragment
import com.hello.spiralworktask.view.login.createaccount.CreateAccountFragmentModule
import com.hello.spiralworktask.view.login.createaccount.InputDetailsFragment
import com.hello.spiralworktask.view.login.createaccount.InputEmailFragment
import com.hello.spiralworktask.view.login.createaccount.InputPasswordFragment
import com.hello.spiralworktask.view.login.emaillogin.EmailLoginFragment
import com.hello.spiralworktask.view.login.emaillogin.EmailLoginFragmentModule
import com.hello.spiralworktask.view.login.forgotpassword.ForgotPasswordFragment
import com.hello.spiralworktask.view.login.forgotpassword.ForgotPasswordFragmentModule
import com.hello.spiralworktask.view.login.loginmain.LoginMainFragment
import com.hello.spiralworktask.view.login.loginmain.LoginMainFragmentModule
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

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

  @PerFragment
  @ContributesAndroidInjector(modules = [(CreateAccountFragmentModule::class)])
  abstract fun bindInputDetailsFragment(): InputDetailsFragment

  @PerFragment
  @ContributesAndroidInjector(modules = [(CreateAccountFragmentModule::class)])
  abstract fun bindInputEmailFragment(): InputEmailFragment

  @PerFragment
  @ContributesAndroidInjector(modules = [(CreateAccountFragmentModule::class)])
  abstract fun bindInputPasswordFragment(): InputPasswordFragment
}