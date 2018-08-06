package com.hello.spiralworktask.ui.login.emaillogin

import android.arch.lifecycle.ViewModelProvider
import android.content.Context
import android.content.res.ColorStateList
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hello.spiralworktask.R
import com.hello.spiralworktask.libs.android.BaseFragment
import com.hello.spiralworktask.libs.ext.getViewModel
import com.hello.spiralworktask.libs.ext.observe
import com.hello.spiralworktask.libs.ext.snackError
import com.hello.spiralworktask.libs.ext.withViewModel
import com.hello.spiralworktask.ui.login.emaillogin.EmailLoginViewModel.LoginState.Error
import com.hello.spiralworktask.ui.login.emaillogin.EmailLoginViewModel.LoginState.Invalid
import com.hello.spiralworktask.ui.login.emaillogin.EmailLoginViewModel.LoginState.LoggingIn
import com.hello.spiralworktask.ui.login.emaillogin.EmailLoginViewModel.LoginState.Success
import com.hello.spiralworktask.ui.login.emaillogin.EmailLoginViewModel.LoginState.Validated
import com.jakewharton.rxbinding2.support.v7.widget.RxToolbar
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxTextView
import kotlinx.android.synthetic.main.fragment_email_login.confirmFabButton
import kotlinx.android.synthetic.main.fragment_email_login.confirmProgress
import kotlinx.android.synthetic.main.fragment_email_login.emailAddressEditText
import kotlinx.android.synthetic.main.fragment_email_login.passwordEditText
import kotlinx.android.synthetic.main.fragment_email_login.root
import kotlinx.android.synthetic.main.fragment_email_login.showPasswordTextView
import kotlinx.android.synthetic.main.fragment_email_login.toolbar
import org.jetbrains.anko.backgroundColor
import javax.inject.Inject

class EmailLoginFragment : BaseFragment() {

  companion object {
    private const val SHOW_PASSWORD = 0
    private const val HIDE_PASSWORD = 1
    fun newInstance(): EmailLoginFragment =
      EmailLoginFragment()
  }

  interface EmailLoginInteraction {
    fun onLoginSuccess()
    fun onBackButtonClicked()
  }

  @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
  private var listener: EmailLoginInteraction? = null
  private var viewModel: EmailLoginViewModel? = null

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? = inflater.inflate(R.layout.fragment_email_login, container, false)

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)

    viewModel = getViewModel(viewModelFactory)

    subscribeToObservables()
    observeToViewModel()
  }

  override fun onAttach(context: Context) {
    super.onAttach(context)
    if (context is EmailLoginInteraction) {
      listener = context
    } else {
      throw RuntimeException(context.toString() + " must implement EmailLoginInteraction")
    }
  }

  override fun onDetach() {
    super.onDetach()
    listener = null
  }

  private fun observeToViewModel() {
    withViewModel<EmailLoginViewModel>(viewModelFactory) {
      observe(loginState) {
        when (it) {
          Invalid -> {
            confirmProgress.visibility = View.INVISIBLE
            confirmFabButton.apply {
              isClickable = false
              setImageResource(0)
              backgroundTintList = ColorStateList.valueOf(
                  resources.getColor(R.color.material_color_white_20_percent)
              )
            }
          }
          Error -> {
            confirmProgress.visibility = View.INVISIBLE
            confirmFabButton.apply {
              isClickable = true
              setImageResource(R.drawable.ic_next)
              backgroundTintList = ColorStateList.valueOf(
                  resources.getColor(R.color.material_color_white_50_percent)
              )
            }
            root.snackError("Error", "Login Failed. Please try again.", Snackbar.LENGTH_SHORT) {
              view.backgroundColor = ContextCompat.getColor(context, R.color.material_color_white)
            }

          }
          Validated -> {
            confirmProgress.visibility = View.INVISIBLE
            confirmFabButton.apply {
              isClickable = true
              setImageResource(R.drawable.ic_next)
              backgroundTintList = ColorStateList.valueOf(
                  resources.getColor(R.color.material_color_white_50_percent)
              )
            }
          }
          LoggingIn -> {
            confirmProgress.visibility = View.VISIBLE
            confirmFabButton.apply {
              isClickable = false
              setImageResource(R.drawable.ic_next)
              backgroundTintList = ColorStateList.valueOf(
                  resources.getColor(R.color.material_color_white_50_percent)
              )
            }
          }
          Success -> {
            confirmProgress.visibility = View.INVISIBLE
            confirmFabButton.apply {
              isClickable = false
              setImageResource(R.drawable.ic_check)
              backgroundTintList = ColorStateList.valueOf(
                  resources.getColor(R.color.material_color_white_50_percent)
              )
            }
            listener?.onLoginSuccess()
          }
        }
      }
    }
  }

  private fun subscribeToObservables() {
    disposableContainer.add(
        RxView.clicks(showPasswordTextView)
            .doOnNext { togglePasswordVisibility() }
            .subscribe { showPasswordTextView.showNext() })
    disposableContainer.add(
        RxToolbar.navigationClicks(toolbar)
            .subscribe { listener?.onBackButtonClicked() })
    disposableContainer.add(
        RxTextView.textChanges(emailAddressEditText)
            .subscribe { viewModel?.email = it })
    disposableContainer.add(
        RxTextView.textChanges(passwordEditText)
            .subscribe { viewModel?.password = it })
    disposableContainer.add(
        RxView.clicks(confirmFabButton)
            .subscribe { viewModel?.submitLoginDetails() })

  }

  private fun togglePasswordVisibility() =
    passwordEditText.apply {
      transformationMethod = when (showPasswordTextView.displayedChild) {
        SHOW_PASSWORD -> HideReturnsTransformationMethod.getInstance()
        else -> PasswordTransformationMethod.getInstance()
      }
      setSelection(passwordEditText.text.length)
    }

}
