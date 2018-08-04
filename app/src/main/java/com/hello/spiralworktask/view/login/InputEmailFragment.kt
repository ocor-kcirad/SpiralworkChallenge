package com.hello.spiralworktask.view.login

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hello.spiralworktask.R
import kotlinx.android.synthetic.main.fragment_input_email.confirmFabButton
import kotlinx.android.synthetic.main.fragment_input_email.emailAddressEditText
import kotlinx.android.synthetic.main.fragment_input_email.toolbar

class InputEmailFragment : Fragment() {

  companion object {
    fun newInstance(): InputEmailFragment = InputEmailFragment()
  }

  private var listener: InputEmailInteraction? = null

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? = inflater.inflate(R.layout.fragment_input_email, container, false)

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    toolbar.setNavigationOnClickListener { listener?.onBackButtonClicked() }
    confirmFabButton.setOnClickListener {
      listener?.onSubmitEmail(emailAddressEditText.text.toString())
    }
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

  interface InputEmailInteraction {
    fun onSubmitEmail(email: String)
    fun onBackButtonClicked()
  }

}
