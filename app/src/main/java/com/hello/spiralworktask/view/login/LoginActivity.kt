package com.hello.spiralworktask.view.login

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.hello.spiralworktask.R
import com.hello.spiralworktask.libs.common.inTransaction
import com.hello.spiralworktask.libs.common.replaceFragment
import com.hello.spiralworktask.view.login.EmailLoginFragment.EmailLoginInteraction
import com.hello.spiralworktask.view.login.InputDetailsFragment.InputDetailsInteraction
import com.hello.spiralworktask.view.login.InputEmailFragment.InputEmailInteraction
import com.hello.spiralworktask.view.login.InputPasswordFragment.InputPasswordInteraction
import com.hello.spiralworktask.view.login.LoginMainFragment.LoginMainInteraction

class LoginActivity : AppCompatActivity(),
    LoginMainInteraction,
    EmailLoginInteraction,
    InputDetailsInteraction,
    InputEmailInteraction,
    InputPasswordInteraction {

  override fun onSubmitEmail(email: String) {
    supportFragmentManager.inTransaction {
      setCustomAnimations(R.anim.right_to_left, R.anim.left_to_right)
      replace(R.id.fragmentContainer, InputPasswordFragment.newInstance())
    }
  }

  override fun onSubmitPassword(password: String) {

  }

  override fun onSubmitDetails(
    firstName: String,
    lastName: String
  ) {
    supportFragmentManager.inTransaction {
      setCustomAnimations(R.anim.right_to_left, R.anim.left_to_right)
      replace(R.id.fragmentContainer, InputEmailFragment.newInstance())
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_login)
    replaceFragment(R.id.fragmentContainer, LoginMainFragment.newInstance())
  }

  override fun onLoginClicked() {
    supportFragmentManager.inTransaction {
      setCustomAnimations(R.anim.right_to_left, R.anim.left_to_right)
      replace(R.id.fragmentContainer, EmailLoginFragment.newInstance())
    }
  }

  override fun onCreateAccountClicked() {
    supportFragmentManager.inTransaction {
      setCustomAnimations(R.anim.right_to_left, R.anim.left_to_right)
      replace(R.id.fragmentContainer, InputDetailsFragment.newInstance())
    }
  }

  override fun onForgotPasswordClicked() {
    supportFragmentManager.inTransaction {
      setCustomAnimations(R.anim.right_to_left, R.anim.left_to_right)
      replace(R.id.fragmentContainer, ForgotPasswordFragment.newInstance())
    }
  }

}
