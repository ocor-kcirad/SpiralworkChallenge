package com.hello.spiralworktask.ui.login

import android.arch.lifecycle.ViewModelProvider
import android.os.Bundle
import com.hello.spiralworktask.R
import com.hello.spiralworktask.libs.android.BaseActivity
import com.hello.spiralworktask.libs.ext.getViewModel
import com.hello.spiralworktask.libs.ext.inTransaction
import com.hello.spiralworktask.libs.ext.observe
import com.hello.spiralworktask.libs.ext.replaceFragment
import com.hello.spiralworktask.libs.ext.withViewModel
import com.hello.spiralworktask.navigation.RegisterPageNavigator
import com.hello.spiralworktask.navigation.WelcomePageNavigator
import com.hello.spiralworktask.ui.login.LoginViewModel.SessionState.Resumed
import com.hello.spiralworktask.ui.login.LoginViewModel.SessionState.Started
import com.hello.spiralworktask.ui.login.emaillogin.EmailLoginFragment
import com.hello.spiralworktask.ui.login.emaillogin.EmailLoginFragment.EmailLoginInteraction
import com.hello.spiralworktask.ui.login.loginmain.LoginMainFragment
import com.hello.spiralworktask.ui.login.loginmain.LoginMainFragment.LoginMainInteraction
import javax.inject.Inject

class LoginActivity : BaseActivity(),
    LoginMainInteraction,
    EmailLoginInteraction {

  @Inject lateinit var welcomePageNavigator: WelcomePageNavigator
  @Inject lateinit var registerPageNavigator: RegisterPageNavigator
  @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_login)
    replaceFragment(R.id.fragmentContainer, LoginMainFragment.newInstance())
    observeViewModel()
    getViewModel<LoginViewModel>(viewModelFactory).checkSession()
  }

  private fun observeViewModel() {
    withViewModel<LoginViewModel>(viewModelFactory) {
      observe(sessionState) {
        when (it) {
          Started -> {
            welcomePageNavigator.navigate(this@LoginActivity)
          }
          Resumed -> {
            welcomePageNavigator.navigate(this@LoginActivity)
          }
        }
      }
    }
  }

  override fun onLoginClicked() {
    supportFragmentManager.inTransaction {
      setCustomAnimations(
          R.anim.enter_from_right, R.anim.exit_to_left,
          R.anim.enter_from_left, R.anim.exit_to_right
      )
      replace(R.id.fragmentContainer, EmailLoginFragment.newInstance())
      addToBackStack(null)
    }
  }

  override fun onCreateAccountClicked() {
    registerPageNavigator.navigate(this)
  }

  override fun onLoginSuccess() {
    getViewModel<LoginViewModel>(viewModelFactory).startSession()
  }

  override fun onCloseButtonClicked() {
    finish()
  }

  override fun onBackButtonClicked() {
    supportFragmentManager.popBackStack()
  }

}
