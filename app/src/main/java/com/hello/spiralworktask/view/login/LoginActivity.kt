package com.hello.spiralworktask.view.login

import android.os.Bundle
import com.hello.spiralworktask.R
import com.hello.spiralworktask.libs.android.BaseActivity
import com.hello.spiralworktask.libs.ext.inTransaction
import com.hello.spiralworktask.libs.ext.replaceFragment
import com.hello.spiralworktask.navigation.WelcomePageNavigator
import com.hello.spiralworktask.view.login.createaccount.InputDetailsFragment
import com.hello.spiralworktask.view.login.createaccount.InputDetailsFragment.InputDetailsInteraction
import com.hello.spiralworktask.view.login.createaccount.InputEmailFragment
import com.hello.spiralworktask.view.login.createaccount.InputEmailFragment.InputEmailInteraction
import com.hello.spiralworktask.view.login.createaccount.InputPasswordFragment
import com.hello.spiralworktask.view.login.createaccount.InputPasswordFragment.InputPasswordInteraction
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
    InputDetailsInteraction,
    InputEmailInteraction,
    InputPasswordInteraction,
    ForgotPasswordInteraction {

  @Inject lateinit var welcomePageNavigator: WelcomePageNavigator

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
    supportFragmentManager.inTransaction {
      setCustomAnimations(
          R.anim.enter_from_left, R.anim.exit_to_left,
          R.anim.enter_from_right, R.anim.exit_to_right
      )
      replace(R.id.fragmentContainer, InputDetailsFragment.newInstance())
      addToBackStack(null)
    }
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

  override fun onSubmitDetails(
    firstName: String,
    lastName: String
  ) {
    supportFragmentManager.inTransaction {
      setCustomAnimations(
          R.anim.enter_from_left, R.anim.exit_to_left,
          R.anim.enter_from_right, R.anim.exit_to_right
      )
      replace(R.id.fragmentContainer, InputEmailFragment.newInstance())
      addToBackStack(null)
    }
  }

  override fun onSubmitEmail(email: String) {
    supportFragmentManager.inTransaction {
      setCustomAnimations(
          R.anim.enter_from_left, R.anim.exit_to_left,
          R.anim.enter_from_right, R.anim.exit_to_right
      )
      replace(R.id.fragmentContainer, InputPasswordFragment.newInstance())
      addToBackStack(null)
    }
  }

  override fun onSubmitPassword(password: String) {

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
