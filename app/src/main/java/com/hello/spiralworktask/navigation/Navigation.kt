package com.hello.spiralworktask.navigation

import android.app.Activity
import com.hello.spiralworktask.R
import com.hello.spiralworktask.ui.login.LoginActivity
import com.hello.spiralworktask.ui.register.RegisterActivity
import com.hello.spiralworktask.ui.welcome.WelcomeUserActivity
import org.jetbrains.anko.clearTask
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.newTask
import javax.inject.Inject

class WelcomePageNavigator @Inject constructor() {

  fun navigate(
    activity: Activity,
    clearTask: Boolean = false
  ) = activity.apply {

    val intent = intentFor<WelcomeUserActivity>()

    if (clearTask) {
      intent.clearTask()
          .newTask()
    }

    startActivity(intent)
    finish()
    overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right)
  }
}

class LoginPageNavigator @Inject constructor() {

  fun navigate(activity: Activity) = activity.apply {
    val intent = intentFor<LoginActivity>()
    startActivity(intent)
    finish()
    overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right)
  }

}

class RegisterPageNavigator @Inject constructor() {

  fun navigate(activity: Activity) = activity.apply {
    val intent = intentFor<RegisterActivity>()
    startActivity(intent)
    overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left)
  }

}