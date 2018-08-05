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
import kotlinx.android.synthetic.main.fragment_input_email.confirmFabButton
import kotlinx.android.synthetic.main.fragment_input_email.emailAddressEditText
import kotlinx.android.synthetic.main.fragment_input_email.toolbar

class InputEmailFragment : BaseFragment() {

  companion object {
    fun newInstance(): InputEmailFragment = InputEmailFragment()
  }

  interface InputEmailInteraction {
    fun onSubmitEmail(email: String)
    fun onBackButtonClicked()
  }

  private var listener: InputEmailInteraction? = null

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? = inflater.inflate(R.layout.fragment_input_email, container, false)

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    subscribeToObservables()
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

  private fun subscribeToObservables() {
    disposableContainer.add(RxToolbar.navigationClicks(toolbar)
        .subscribe { listener?.onBackButtonClicked() })
    disposableContainer.add(
        RxView.clicks(confirmFabButton)
            .subscribe { listener?.onSubmitEmail(emailAddressEditText.text.toString()) })
  }

}
