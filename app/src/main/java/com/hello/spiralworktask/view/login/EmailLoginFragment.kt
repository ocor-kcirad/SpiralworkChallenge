package com.hello.spiralworktask.view.login

import android.content.Context
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hello.spiralworktask.R
import com.hello.spiralworktask.libs.android.BaseFragment
import com.jakewharton.rxbinding2.support.v7.widget.RxToolbar
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxTextView
import kotlinx.android.synthetic.main.fragment_email_login.emailAddressEditText
import kotlinx.android.synthetic.main.fragment_email_login.forgotPasswordTextView
import kotlinx.android.synthetic.main.fragment_email_login.passwordEditText
import kotlinx.android.synthetic.main.fragment_email_login.showPasswordTextView
import kotlinx.android.synthetic.main.fragment_email_login.toolbar
import org.jetbrains.anko.support.v4.toast

class EmailLoginFragment : BaseFragment() {

  companion object {
    private const val SHOW_PASSWORD = 0
    private const val HIDE_PASSWORD = 1
    fun newInstance(): EmailLoginFragment = EmailLoginFragment()
  }

  interface EmailLoginInteraction {
    fun onForgotPasswordClicked()
    fun onBackButtonClicked()
  }

  private var listener: EmailLoginInteraction? = null

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? = inflater.inflate(R.layout.fragment_email_login, container, false)

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    subscribeToObservables()
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
            .subscribe { toast(it) })
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
