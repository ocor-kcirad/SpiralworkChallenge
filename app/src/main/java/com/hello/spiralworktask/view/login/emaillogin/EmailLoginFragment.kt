package com.hello.spiralworktask.view.login.emaillogin

import android.arch.lifecycle.Observer
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
import com.hello.spiralworktask.libs.ext.snackError
import com.hello.spiralworktask.view.login.emaillogin.EmailLoginViewModel.LoginState.Error
import com.hello.spiralworktask.view.login.emaillogin.EmailLoginViewModel.LoginState.LoggedIn
import com.jakewharton.rxbinding2.support.v7.widget.RxToolbar
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxTextView
import kotlinx.android.synthetic.main.fragment_email_login.confirmFabButton
import kotlinx.android.synthetic.main.fragment_email_login.confirmProgress
import kotlinx.android.synthetic.main.fragment_email_login.emailAddressEditText
import kotlinx.android.synthetic.main.fragment_email_login.forgotPasswordTextView
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
    fun onForgotPasswordClicked()
    fun onLoginSuccess()
    fun onBackButtonClicked()
  }

  @Inject lateinit var viewModel: EmailLoginViewModel
  private var listener: EmailLoginInteraction? = null

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? = inflater.inflate(R.layout.fragment_email_login, container, false)

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
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
    viewModel.loginState.observe(this, Observer {

      confirmFabButton.apply {

        val fabBackgroundTint =
          if (it?.fabEnabled == true) R.color.material_color_white_50_percent
          else R.color.material_color_white_20_percent

        val fabImage =
          if (it?.fabEnabled == true) R.drawable.ic_next
          else 0

        isClickable = it?.fabEnabled == true
        setImageResource(fabImage)
        backgroundTintList = ColorStateList.valueOf(resources.getColor(fabBackgroundTint))
      }

      confirmProgress.apply {
        visibility = if (it?.progressVisibility == true) View.VISIBLE
        else View.INVISIBLE
      }
      if (it == LoggedIn) {
        confirmFabButton.setImageResource(R.drawable.ic_check)
        listener?.onLoginSuccess()
      } else if (it == Error) {
        root.snackError("Error", "Login Failed. Please try again.", Snackbar.LENGTH_SHORT) {
          view.backgroundColor = ContextCompat.getColor(context, R.color.material_color_white)
        }
      }
    })
  }

  private fun subscribeToObservables() {
    disposableContainer.add(
        RxView.clicks(forgotPasswordTextView)
            .subscribe { listener?.onForgotPasswordClicked() })
    disposableContainer.add(
        RxView.clicks(showPasswordTextView)
            .doOnNext { togglePasswordVisibility() }
            .subscribe { showPasswordTextView.showNext() })
    disposableContainer.add(
        RxToolbar.navigationClicks(toolbar)
            .subscribe { listener?.onBackButtonClicked() })
    disposableContainer.add(
        RxTextView.textChanges(emailAddressEditText)
            .subscribe { viewModel.email = it })
    disposableContainer.add(
        RxTextView.textChanges(passwordEditText)
            .subscribe { viewModel.password = it })
    disposableContainer.add(
        RxView.clicks(confirmFabButton)
            .subscribe { viewModel.submitLoginDetails() })

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
