package com.hello.spiralworktask.view.login.createaccount

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
import kotlinx.android.synthetic.main.fragment_input_password.confirmFabButton
import kotlinx.android.synthetic.main.fragment_input_password.passwordEditText
import kotlinx.android.synthetic.main.fragment_input_password.showPasswordTextView
import kotlinx.android.synthetic.main.fragment_input_password.toolbar
import javax.inject.Inject

class InputPasswordFragment : BaseFragment() {

  companion object {
    private const val SHOW_PASSWORD = 0
    private const val HIDE_PASSWORD = 1
    fun newInstance(): InputPasswordFragment =
      InputPasswordFragment()
  }

  interface InputPasswordInteraction {
    fun onSubmitPassword(password: String)
    fun onBackButtonClicked()
  }

  @Inject lateinit var viewModel: CreateAccountViewModel
  private var listener: InputPasswordInteraction? = null

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? = inflater.inflate(R.layout.fragment_input_password, container, false)

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    subscribeToObservables()
  }

  override fun onAttach(context: Context?) {
    super.onAttach(context)
    if (context is InputPasswordInteraction) {
      listener = context
    } else {
      throw RuntimeException(context!!.toString() + " must implement InputPasswordInteraction")
    }
  }

  override fun onDetach() {
    super.onDetach()
    listener = null
  }

  private fun subscribeToObservables() {
    disposableContainer.add(RxToolbar.navigationClicks(toolbar)
        .subscribe { listener?.onBackButtonClicked() })
    disposableContainer.add(RxView.clicks(showPasswordTextView)
        .doOnNext { togglePasswordVisibility() }
        .subscribe { showPasswordTextView.showNext() })
    disposableContainer.add(RxView.clicks(confirmFabButton)
        .subscribe { listener?.onSubmitPassword(passwordEditText.text.toString()) })
  }

  private fun togglePasswordVisibility() {
    passwordEditText.apply {
      transformationMethod = when (showPasswordTextView.displayedChild) {
        SHOW_PASSWORD -> HideReturnsTransformationMethod.getInstance()
        else -> PasswordTransformationMethod.getInstance()
      }
      setSelection(passwordEditText.text.length)
    }
  }

}
