package com.hello.spiralworktask.view.login.forgotpassword

import android.arch.lifecycle.ViewModelProvider
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hello.spiralworktask.R
import com.hello.spiralworktask.libs.android.BaseFragment
import com.jakewharton.rxbinding2.support.v7.widget.RxToolbar
import com.jakewharton.rxbinding2.view.RxView
import kotlinx.android.synthetic.main.fragment_forgot_password.confirmFabButton
import kotlinx.android.synthetic.main.fragment_forgot_password.emailEditText
import kotlinx.android.synthetic.main.fragment_forgot_password.toolbar
import javax.inject.Inject

class ForgotPasswordFragment : BaseFragment() {

  companion object {
    fun newInstance(): ForgotPasswordFragment =
      ForgotPasswordFragment()
  }

  interface ForgotPasswordInteraction {
    fun onRequestPassword(email: String)
    fun onBackButtonClicked()
  }

  @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
  private var listener: ForgotPasswordInteraction? = null

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? = inflater.inflate(R.layout.fragment_forgot_password, container, false)

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    subscribeToObservables()
  }

  override fun onAttach(context: Context?) {
    super.onAttach(context)
    if (context is ForgotPasswordInteraction) {
      listener = context
    } else {
      throw RuntimeException(context!!.toString() + " must implement ForgotPasswordInteraction")
    }
  }

  override fun onDetach() {
    super.onDetach()
    listener = null
  }

  private fun subscribeToObservables() {
    disposableContainer.add(
        RxToolbar.navigationClicks(toolbar)
            .subscribe { listener?.onBackButtonClicked() })
    disposableContainer.add(
        RxView.clicks(confirmFabButton)
            .subscribe { listener?.onRequestPassword(emailEditText.text.toString()) })
  }

}
