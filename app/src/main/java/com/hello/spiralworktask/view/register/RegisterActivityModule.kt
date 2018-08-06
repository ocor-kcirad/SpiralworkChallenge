package com.hello.spiralworktask.view.register

import com.hello.spiralworktask.libs.di.scopes.PerFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
internal abstract class RegisterActivityModule {

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