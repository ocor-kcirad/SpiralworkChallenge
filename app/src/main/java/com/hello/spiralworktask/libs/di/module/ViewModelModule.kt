package com.hello.spiralworktask.libs.di.module

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.hello.spiralworktask.libs.di.ViewModelFactory
import com.hello.spiralworktask.libs.di.ViewModelKey
import com.hello.spiralworktask.ui.login.LoginViewModel
import com.hello.spiralworktask.ui.login.emaillogin.EmailLoginViewModel
import com.hello.spiralworktask.ui.login.forgotpassword.ForgotPasswordViewModel
import com.hello.spiralworktask.ui.register.RegisterAccountViewModel
import com.hello.spiralworktask.ui.welcome.WelcomeUserViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

  @Binds
  internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

  @Binds
  @IntoMap
  @ViewModelKey(EmailLoginViewModel::class)
  internal abstract fun bindEmailLoginViewModel(viewModel: EmailLoginViewModel): ViewModel

  @Binds
  @IntoMap
  @ViewModelKey(RegisterAccountViewModel::class)
  abstract fun bindRegisterAccountViewModel(viewModel: RegisterAccountViewModel): ViewModel

  @Binds
  @IntoMap
  @ViewModelKey(LoginViewModel::class)
  abstract fun bindLoginViewModel(viewModel: LoginViewModel): ViewModel

  @Binds
  @IntoMap
  @ViewModelKey(WelcomeUserViewModel::class)
  abstract fun bindWelcomeUserViewModel(viewModel: WelcomeUserViewModel): ViewModel

  @Binds
  @IntoMap
  @ViewModelKey(ForgotPasswordViewModel::class)
  abstract fun bindForgotPasswordViewModel(viewModel: ForgotPasswordViewModel): ViewModel
}