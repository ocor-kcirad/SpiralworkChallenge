package com.hello.spiralworktask.ui.register

import android.os.Bundle
import com.hello.spiralworktask.R
import com.hello.spiralworktask.libs.android.BaseActivity
import com.hello.spiralworktask.libs.ext.inTransaction
import com.hello.spiralworktask.libs.ext.replaceFragment
import com.hello.spiralworktask.navigation.WelcomePageNavigator
import com.hello.spiralworktask.ui.register.InputDetailsFragment.InputDetailsInteraction
import com.hello.spiralworktask.ui.register.InputEmailFragment.InputEmailInteraction
import com.hello.spiralworktask.ui.register.InputPasswordFragment.InputPasswordInteraction
import javax.inject.Inject

class RegisterActivity : BaseActivity(),
    InputDetailsInteraction,
    InputEmailInteraction,
    InputPasswordInteraction {

  @Inject lateinit var welcomePageNavigator: WelcomePageNavigator

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_login)
    replaceFragment(R.id.fragmentContainer, InputDetailsFragment.newInstance())
  }

  override fun onDetailsSubmitted() {
    supportFragmentManager.inTransaction {
      setCustomAnimations(
          R.anim.enter_from_right, R.anim.exit_to_left,
          R.anim.enter_from_left, R.anim.exit_to_right
      )
      replace(R.id.fragmentContainer, InputEmailFragment.newInstance())
      addToBackStack(null)
    }
  }

  override fun onEmailSubmitted() {
    supportFragmentManager.inTransaction {
      setCustomAnimations(
          R.anim.enter_from_right, R.anim.exit_to_left,
          R.anim.enter_from_left, R.anim.exit_to_right
      )
      replace(R.id.fragmentContainer, InputPasswordFragment.newInstance())
      addToBackStack(null)
    }
  }

  override fun onAccountCreated() {
    welcomePageNavigator.navigate(this, true)
  }

  override fun onBackButtonClicked() {
    supportFragmentManager.popBackStack()
  }

  override fun onCloseButtonClicked() {
    finish()
    overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right)
  }
}
