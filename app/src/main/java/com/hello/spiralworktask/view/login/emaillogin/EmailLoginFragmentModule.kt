package com.hello.spiralworktask.view.login.emaillogin

import android.arch.lifecycle.ViewModel
import com.hello.spiralworktask.libs.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
internal abstract class EmailLoginFragmentModule {

  @Binds
  @IntoMap
  @ViewModelKey(EmailLoginViewModel::class)
  abstract fun bindEmailLoginViewModel(viewModel: EmailLoginViewModel): ViewModel

}