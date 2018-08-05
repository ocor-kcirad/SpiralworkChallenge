package com.hello.spiralworktask.navigation

import android.app.Activity
import com.hello.spiralworktask.view.welcome.WelcomeActivity
import org.jetbrains.anko.intentFor
import javax.inject.Inject

class WelcomePageNavigator @Inject constructor() {

  fun navigate(activity: Activity) = activity.startActivity(activity.intentFor<WelcomeActivity>())

}