package com.hello.spiralworktask.view.login

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hello.spiralworktask.R
import com.hello.spiralworktask.libs.android.BaseFragment
import com.jakewharton.rxbinding2.support.v7.widget.RxToolbar
import com.jakewharton.rxbinding2.view.RxView
import kotlinx.android.synthetic.main.fragment_input_details.confirmFabButton
import kotlinx.android.synthetic.main.fragment_input_details.firstNameEditText
import kotlinx.android.synthetic.main.fragment_input_details.lastNameEditText
import kotlinx.android.synthetic.main.fragment_input_details.toolbar

class InputDetailsFragment : BaseFragment() {

  companion object {
    fun newInstance(): InputDetailsFragment = InputDetailsFragment()
  }

  interface InputDetailsInteraction {
    fun onSubmitDetails(
      firstName: String,
      lastName: String
    )

    fun onBackButtonClicked()
  }

  private var listener: InputDetailsInteraction? = null

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? = inflater.inflate(R.layout.fragment_input_details, container, false)

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    subscribeToObservables()
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

  private fun subscribeToObservables() {
    disposableContainer.add(
        RxToolbar.navigationClicks(toolbar)
            .subscribe { listener?.onBackButtonClicked() })
    disposableContainer.add(
        RxView.clicks(confirmFabButton)
            .subscribe {
              listener?.onSubmitDetails(
                  firstNameEditText.text.toString(), lastNameEditText.text.toString()
              )
            })
  }

}
