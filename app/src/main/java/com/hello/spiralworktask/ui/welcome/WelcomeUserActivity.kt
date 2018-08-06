package com.hello.spiralworktask.ui.welcome

import android.arch.lifecycle.ViewModelProvider
import android.os.Bundle
import com.hello.spiralworktask.R
import com.hello.spiralworktask.libs.android.BaseActivity
import com.hello.spiralworktask.libs.ext.getViewModel
import com.hello.spiralworktask.libs.ext.observe
import com.hello.spiralworktask.libs.ext.withViewModel
import com.hello.spiralworktask.navigation.LoginPageNavigator
import com.jakewharton.rxbinding2.view.RxView
import kotlinx.android.synthetic.main.activity_welcome.greetingsTextView
import kotlinx.android.synthetic.main.activity_welcome.logoutTextView
import javax.inject.Inject

class WelcomeUserActivity : BaseActivity() {

  @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
  @Inject lateinit var loginPageNavigator: LoginPageNavigator

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_welcome)
    subscribeToObservables()
    observeToViewModel()

    getViewModel<WelcomeUserViewModel>(viewModelFactory).apply {
      checkSession()
      loadUser()
    }
  }

  private fun subscribeToObservables() {
    disposableContainer.add(
        RxView.clicks(logoutTextView).subscribe {
          getViewModel<WelcomeUserViewModel>(viewModelFactory).logout()
        })

  }

  private fun observeToViewModel() {
    withViewModel<WelcomeUserViewModel>(viewModelFactory) {
      observe(sessionState) {
        when (it) {
          true -> {
            //No Op
          }
          false -> {
            loginPageNavigator.navigate(this@WelcomeUserActivity)
          }
        }
      }
      observe(user) {
        it?.let {
          greetingsTextView.text = getString(R.string.user_greetings, it.firstName, it.lastName)
        }
      }
    }
  }
}
