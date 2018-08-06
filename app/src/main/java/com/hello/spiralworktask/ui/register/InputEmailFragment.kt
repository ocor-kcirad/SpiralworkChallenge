package com.hello.spiralworktask.ui.register

import android.arch.lifecycle.ViewModelProvider
import android.content.Context
import android.content.res.ColorStateList
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hello.spiralworktask.R
import com.hello.spiralworktask.libs.android.BaseFragment
import com.hello.spiralworktask.libs.ext.getViewModel
import com.hello.spiralworktask.libs.ext.observe
import com.hello.spiralworktask.libs.ext.snackError
import com.hello.spiralworktask.libs.ext.withViewModel
import com.hello.spiralworktask.ui.register.RegisterAccountViewModel.EmailInputState.EmailAccepted
import com.hello.spiralworktask.ui.register.RegisterAccountViewModel.EmailInputState.ErrorEmailVerification
import com.hello.spiralworktask.ui.register.RegisterAccountViewModel.EmailInputState.InvalidEmail
import com.jakewharton.rxbinding2.support.v7.widget.RxToolbar
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxTextView
import kotlinx.android.synthetic.main.fragment_input_email.confirmFabButton
import kotlinx.android.synthetic.main.fragment_input_email.emailAddressEditText
import kotlinx.android.synthetic.main.fragment_input_email.root
import kotlinx.android.synthetic.main.fragment_input_email.toolbar
import org.jetbrains.anko.backgroundColor
import javax.inject.Inject

class InputEmailFragment : BaseFragment() {

  companion object {
    fun newInstance(): InputEmailFragment =
      InputEmailFragment()
  }

  interface InputEmailInteraction {
    fun onEmailSubmitted()
    fun onBackButtonClicked()
  }

  @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
  private var listener: InputEmailInteraction? = null
  private var viewModel: RegisterAccountViewModel? = null
  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? = inflater.inflate(R.layout.fragment_input_email, container, false)

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    viewModel = activity?.getViewModel(viewModelFactory)
    subscribeToObservables()
    observeToViewModel()
  }

  override fun onAttach(context: Context?) {
    super.onAttach(context)
    if (context is InputEmailInteraction) {
      listener = context
    } else {
      throw RuntimeException(context!!.toString() + " must implement InputEmailInteraction")
    }
  }

  override fun onDetach() {
    super.onDetach()
    listener = null
  }

  private fun observeToViewModel() {
    activity?.withViewModel<RegisterAccountViewModel>(viewModelFactory) {
      observe(emailInputState) {
        when (it) {
          EmailAccepted -> {
            confirmFabButton.apply {
              isClickable = true
              setImageResource(R.drawable.ic_next)
              backgroundTintList = ColorStateList.valueOf(
                  resources.getColor(R.color.material_color_white_50_percent)
              )
            }
          }
          InvalidEmail -> {
            confirmFabButton.apply {
              isClickable = false
              setImageResource(0)
              backgroundTintList = ColorStateList.valueOf(
                  resources.getColor(R.color.material_color_white_20_percent)
              )
            }
          }
          is ErrorEmailVerification -> {
            root.snackError("Error", it.error, Snackbar.LENGTH_SHORT) {
              view.backgroundColor = ContextCompat.getColor(context, R.color.material_color_white)
            }
          }
        }
      }
    }
  }

  private fun subscribeToObservables() {
    disposableContainer.add(RxToolbar.navigationClicks(toolbar)
        .subscribe { listener?.onBackButtonClicked() })
    disposableContainer.add(RxView.clicks(confirmFabButton)
        .subscribe { listener?.onEmailSubmitted() })
    disposableContainer.add(RxTextView.afterTextChangeEvents(emailAddressEditText)
        .map {it.view().text}
        .subscribe { viewModel?.email = it })
  }

}
