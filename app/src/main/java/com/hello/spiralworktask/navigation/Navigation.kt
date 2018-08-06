package com.hello.spiralworktask.navigation

import android.app.Activity
import com.hello.spiralworktask.R
import com.hello.spiralworktask.view.login.LoginActivity
import com.hello.spiralworktask.view.register.RegisterActivity
import com.hello.spiralworktask.view.welcome.WelcomeUserActivity
import org.jetbrains.anko.intentFor
import javax.inject.Inject

class WelcomePageNavigator @Inject constructor() {

  fun navigate(activity: Activity) {
    activity.startActivity(activity.intentFor<WelcomeUserActivity>())
    activity.finish()
    activity.overridePendingTransition(R.anim.slide_in_top, 0)
  }

}

class LoginPageNavigator @Inject constructor() {

  fun navigate(activity: Activity) {
    activity.startActivity(activity.intentFor<LoginActivity>())
    activity.finish()
    activity.overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right)
  }

}

class RegisterPageNavigator @Inject constructor() {

  fun navigate(activity: Activity) {
    activity.startActivity(activity.intentFor<RegisterActivity>())
    activity.overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_left)
  }

}