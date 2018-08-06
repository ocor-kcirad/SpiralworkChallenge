package com.hello.spiralworktask.libs.di.module

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.hello.spiralworktask.libs.di.ViewModelFactory
import com.hello.spiralworktask.libs.di.ViewModelKey
import com.hello.spiralworktask.view.login.emaillogin.EmailLoginViewModel
import com.hello.spiralworktask.view.register.RegisterAccountViewModel
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
}