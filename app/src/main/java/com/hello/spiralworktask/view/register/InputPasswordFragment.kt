package com.hello.spiralworktask.view.register

import android.arch.lifecycle.ViewModelProvider
import android.content.Context
import android.content.res.ColorStateList
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hello.spiralworktask.R
import com.hello.spiralworktask.libs.android.BaseFragment
import com.hello.spiralworktask.libs.arch.Status.ERROR
import com.hello.spiralworktask.libs.arch.Status.LOADING
import com.hello.spiralworktask.libs.arch.Status.SUCCESS
import com.hello.spiralworktask.libs.ext.getViewModel
import com.hello.spiralworktask.libs.ext.observe
import com.hello.spiralworktask.libs.ext.snackError
import com.hello.spiralworktask.libs.ext.withViewModel
import com.hello.spiralworktask.view.register.RegisterAccountViewModel.PasswordInputState.InvalidPassword
import com.hello.spiralworktask.view.register.RegisterAccountViewModel.PasswordInputState.PasswordAccepted
import com.jakewharton.rxbinding2.support.v7.widget.RxToolbar
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxTextView
import kotlinx.android.synthetic.main.fragment_input_password.confirmFabButton
import kotlinx.android.synthetic.main.fragment_input_password.confirmProgress
import kotlinx.android.synthetic.main.fragment_input_password.passwordEditText
import kotlinx.android.synthetic.main.fragment_input_password.root
import kotlinx.android.synthetic.main.fragment_input_password.showPasswordTextView
import kotlinx.android.synthetic.main.fragment_input_password.toolbar
import org.jetbrains.anko.backgroundColor
import javax.inject.Inject

class InputPasswordFragment : BaseFragment() {

  companion object {
    private const val SHOW_PASSWORD = 0
    private const val HIDE_PASSWORD = 1
    fun newInstance(): InputPasswordFragment =
      InputPasswordFragment()
  }

  interface InputPasswordInteraction {
    fun onBackButtonClicked()
    fun onAccountCreated()
  }

  @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
  private var listener: InputPasswordInteraction? = null
  private var viewModel: RegisterAccountViewModel? = null

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? = inflater.inflate(R.layout.fragment_input_password, container, false)

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    viewModel = activity?.getViewModel(viewModelFactory)
    subscribeToObservables()
    observeToViewModel()
  }

  private fun observeToViewModel() {

    activity?.withViewModel<RegisterAccountViewModel>(viewModelFactory) {

      observe(passwordInputState) {
        when (it) {
          PasswordAccepted -> {
            confirmProgress.visibility = View.INVISIBLE
            confirmFabButton.apply {
              isClickable = true
              setImageResource(R.drawable.ic_next)
              backgroundTintList = ColorStateList.valueOf(
                  resources.getColor(R.color.material_color_white_50_percent)
              )
            }
          }
          InvalidPassword -> {
            confirmProgress.visibility = View.INVISIBLE
            confirmFabButton.apply {
              isClickable = false
              setImageResource(0)
              backgroundTintList = ColorStateList.valueOf(
                  resources.getColor(R.color.material_color_white_20_percent)
              )
            }
          }
        }
      }

      observe(accountCreation) {
        when (it?.status) {
          LOADING -> {
            confirmFabButton.isClickable = false
            confirmProgress.visibility = View.VISIBLE
          }
          SUCCESS -> {
            confirmFabButton.isClickable = false
            confirmProgress.visibility = View.INVISIBLE
            confirmFabButton.setImageResource(R.drawable.ic_check)
            listener?.onAccountCreated()
          }
          ERROR -> {
            confirmFabButton.isClickable = true
            confirmProgress.visibility = View.INVISIBLE
            root.snackError("Error", it.errorMessage ?: "Unknown Error", Snackbar.LENGTH_SHORT) {
              view.backgroundColor = ContextCompat.getColor(context, R.color.material_color_white)
            }
          }
        }
      }
    }
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
    disposableContainer.add(RxTextView.afterTextChangeEvents(passwordEditText)
        .map { it.view().text}
        .subscribe { viewModel?.password = it })
    disposableContainer.add(RxView.clicks(confirmFabButton)
        .subscribe { viewModel?.createAccount() })
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
