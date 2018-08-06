package com.hello.spiralworktask.view.register

import android.arch.lifecycle.ViewModelProvider
import android.content.Context
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hello.spiralworktask.R
import com.hello.spiralworktask.libs.android.BaseFragment
import com.hello.spiralworktask.libs.ext.getViewModel
import com.hello.spiralworktask.libs.ext.observe
import com.hello.spiralworktask.libs.ext.withViewModel
import com.hello.spiralworktask.view.register.RegisterAccountViewModel.UserInputState.InvalidUserDetail
import com.hello.spiralworktask.view.register.RegisterAccountViewModel.UserInputState.UserDetailAccepted
import com.jakewharton.rxbinding2.support.v7.widget.RxToolbar
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxTextView
import kotlinx.android.synthetic.main.fragment_input_details.confirmFabButton
import kotlinx.android.synthetic.main.fragment_input_details.firstNameEditText
import kotlinx.android.synthetic.main.fragment_input_details.lastNameEditText
import kotlinx.android.synthetic.main.fragment_input_details.toolbar
import javax.inject.Inject

class InputDetailsFragment : BaseFragment() {

  companion object {
    fun newInstance(): InputDetailsFragment =
      InputDetailsFragment()
  }

  interface InputDetailsInteraction {
    fun onDetailsSubmitted()
    fun onCloseButtonClicked()
  }

  @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
  private var listener: InputDetailsInteraction? = null
  private var viewModel: RegisterAccountViewModel? = null

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? = inflater.inflate(R.layout.fragment_input_details, container, false)

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    viewModel = activity?.getViewModel(viewModelFactory)
    subscribeToObservables()
    observeToViewModel()
  }

  override fun onAttach(context: Context?) {
    super.onAttach(context)
    if (context is InputDetailsInteraction) {
      listener = context
    } else {
      throw RuntimeException(context!!.toString() + " must implement InputDetailsInteraction")
    }
  }

  override fun onDetach() {
    super.onDetach()
    listener = null
  }

  private fun observeToViewModel() {
    activity?.withViewModel<RegisterAccountViewModel>(viewModelFactory) {
      observe(userInputState) {
        when (it) {
          UserDetailAccepted -> {
            confirmFabButton.apply {
              isClickable = true
              setImageResource(R.drawable.ic_next)
              backgroundTintList = ColorStateList.valueOf(
                  resources.getColor(R.color.material_color_white_50_percent)
              )
            }
          }
          InvalidUserDetail -> {
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

    }
  }

  private fun subscribeToObservables() {
    disposableContainer.add(RxTextView.afterTextChangeEvents(firstNameEditText)
        .map { it.editable() }
        .subscribe { viewModel?.firstName = it })
    disposableContainer.add(RxTextView.afterTextChangeEvents(lastNameEditText)
        .map { it.editable() }
        .subscribe { viewModel?.lastName = it })
    disposableContainer.add(RxToolbar.navigationClicks(toolbar)
        .subscribe { listener?.onCloseButtonClicked() })
    disposableContainer.add(RxView.clicks(confirmFabButton)
        .subscribe { listener?.onDetailsSubmitted() })
  }

}
