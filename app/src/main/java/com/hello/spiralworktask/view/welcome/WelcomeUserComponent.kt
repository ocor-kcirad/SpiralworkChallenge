package com.hello.spiralworktask.view.welcome

import com.hello.spiralworktask.libs.di.scopes.PerActivity
import dagger.Subcomponent
import dagger.android.AndroidInjector

@PerActivity
@Subcomponent(modules = [(WelcomeUserModule::class)])
interface WelcomeUserComponent : AndroidInjector<WelcomeUserActivity>