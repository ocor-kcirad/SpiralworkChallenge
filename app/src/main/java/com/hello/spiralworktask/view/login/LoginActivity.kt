package com.hello.spiralworktask.view.login

import android.os.Bundle
import com.hello.spiralworktask.R
import com.hello.spiralworktask.libs.android.BaseActivity
import com.hello.spiralworktask.libs.ext.inTransaction
import com.hello.spiralworktask.libs.ext.replaceFragment
import com.hello.spiralworktask.navigation.RegisterPageNavigator
import com.hello.spiralworktask.navigation.WelcomePageNavigator
import com.hello.spiralworktask.view.login.emaillogin.EmailLoginFragment
import com.hello.spiralworktask.view.login.emaillogin.EmailLoginFragment.EmailLoginInteraction
import com.hello.spiralworktask.view.login.forgotpassword.ForgotPasswordFragment
import com.hello.spiralworktask.view.login.forgotpassword.ForgotPasswordFragment.ForgotPasswordInteraction
import com.hello.spiralworktask.view.login.loginmain.LoginMainFragment
import com.hello.spiralworktask.view.login.loginmain.LoginMainFragment.LoginMainInteraction
import javax.inject.Inject

class LoginActivity : BaseActivity(),
    LoginMainInteraction,
    EmailLoginInteraction,
    ForgotPasswordInteraction {

  @Inject lateinit var welcomePageNavigator: WelcomePageNavigator
  @Inject lateinit var registerPageNavigator: RegisterPageNavigator

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_login)
    replaceFragment(R.id.fragmentContainer, LoginMainFragment.newInstance())
  }

  override fun onLoginClicked() {
    supportFragmentManager.inTransaction {
      setCustomAnimations(
          R.anim.enter_from_left, R.anim.exit_to_left,
          R.anim.enter_from_right, R.anim.exit_to_right
      )
      replace(R.id.fragmentContainer, EmailLoginFragment.newInstance())
      addToBackStack(null)
    }
  }

  override fun onCreateAccountClicked() {
    registerPageNavigator.navigate(this)
  }

  override fun onForgotPasswordClicked() {
    supportFragmentManager.inTransaction {
      setCustomAnimations(
          R.anim.enter_from_left, R.anim.exit_to_left,
          R.anim.enter_from_right, R.anim.exit_to_right
      )
      replace(R.id.fragmentContainer, ForgotPasswordFragment.newInstance())
      addToBackStack(null)
    }
  }

  override fun onRequestPassword(email: String) {

  }

  override fun onLoginSuccess() {
    welcomePageNavigator.navigate(this)
  }

  override fun onCloseButtonClicked() {
    finish()
  }

  override fun onBackButtonClicked() {
    supportFragmentManager.popBackStack()
  }

}
