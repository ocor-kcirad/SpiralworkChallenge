package com.hello.spiralworktask.libs.di.module

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.hello.spiralworktask.libs.di.ViewModelFactory
import com.hello.spiralworktask.libs.di.ViewModelKey
import com.hello.spiralworktask.libs.di.scopes.PerApplication
import com.hello.spiralworktask.view.login.emaillogin.EmailLoginViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@PerApplication
@Module
abstract class ViewModelModule {
  @Binds
  internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

  @Binds
  @IntoMap
  @ViewModelKey(EmailLoginViewModel::class)
  internal abstract fun postListViewModel(viewModel: EmailLoginViewModel): ViewModel
}