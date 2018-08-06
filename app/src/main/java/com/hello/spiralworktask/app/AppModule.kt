package com.hello.spiralworktask.app

import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.hello.spiralworktask.libs.di.component.UserComponent
import com.hello.spiralworktask.libs.di.scopes.PerApplication
import com.hello.spiralworktask.libs.session.UserManager
import dagger.Module
import dagger.Provides

@Module
class AppModule(private val app: App) {

  @Provides
  fun provideSharedPreference(): SharedPreferences =
    PreferenceManager.getDefaultSharedPreferences(app)

  @PerApplication
  @Provides
  fun provideUserManager(
    gson: Gson,
    sharedPreferences: SharedPreferences,
    userComponentBuilder: UserComponent.Builder
  ) = UserManager(gson, sharedPreferences, userComponentBuilder)

  @PerApplication
  @Provides
  fun provideGson(): Gson = GsonBuilder().create()
}