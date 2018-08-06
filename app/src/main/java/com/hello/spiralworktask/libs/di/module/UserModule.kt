package com.hello.spiralworktask.libs.di.module

import com.hello.spiralworktask.libs.di.scopes.PerUser
import com.hello.spiralworktask.model.Session
import dagger.Module
import dagger.Provides

@Module
class UserModule(val session: Session) {

  @Provides
  @PerUser
  fun provideUser(): Session = session

}