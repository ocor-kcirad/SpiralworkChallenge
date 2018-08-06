package com.hello.spiralworktask.ui.register

import com.hello.spiralworktask.libs.di.scopes.PerFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
internal abstract class RegisterActivityModule {

  @PerFragment
  @ContributesAndroidInjector()
  abstract fun bindInputDetailsFragment(): InputDetailsFragment

  @PerFragment
  @ContributesAndroidInjector()
  abstract fun bindInputEmailFragment(): InputEmailFragment

  @PerFragment
  @ContributesAndroidInjector()
  abstract fun bindInputPasswordFragment(): InputPasswordFragment
}