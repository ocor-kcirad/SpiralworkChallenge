package com.hello.spiralworktask.navigation

import android.app.Activity
import com.hello.spiralworktask.R
import com.hello.spiralworktask.ui.login.LoginActivity
import com.hello.spiralworktask.ui.register.RegisterActivity
import com.hello.spiralworktask.ui.welcome.WelcomeUserActivity
import org.jetbrains.anko.clearTask
import org.jetbrains.anko.intentFor
import javax.inject.Inject

class WelcomePageNavigator @Inject constructor() {

  fun navigate(activity: Activity) {
    val intent = activity.intentFor<WelcomeUserActivity>().clearTask()
    activity.startActivity(intent)
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
    activity.overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left)
  }

}