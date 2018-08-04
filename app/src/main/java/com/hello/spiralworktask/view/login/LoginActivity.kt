package com.hello.spiralworktask.view.login

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.hello.spiralworktask.R
import com.hello.spiralworktask.libs.common.inTransaction
import com.hello.spiralworktask.libs.common.replaceFragment
import com.hello.spiralworktask.view.login.EmailLoginFragment.EmailLoginInteraction
import com.hello.spiralworktask.view.login.ForgotPasswordFragment.ForgotPasswordInteraction
import com.hello.spiralworktask.view.login.InputDetailsFragment.InputDetailsInteraction
import com.hello.spiralworktask.view.login.InputEmailFragment.InputEmailInteraction
import com.hello.spiralworktask.view.login.InputPasswordFragment.InputPasswordInteraction
import com.hello.spiralworktask.view.login.LoginMainFragment.LoginMainInteraction

class LoginActivity : AppCompatActivity(),
    LoginMainInteraction,
    EmailLoginInteraction,
    InputDetailsInteraction,
    InputEmailInteraction,
    InputPasswordInteraction,
    ForgotPasswordInteraction {

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

  override fun onCloseButtonClicked() {
    finish()
  }

  override fun onBackButtonClicked() {
    Log.d("Darick", "Pop")
    supportFragmentManager.popBackStack()
  }

}
