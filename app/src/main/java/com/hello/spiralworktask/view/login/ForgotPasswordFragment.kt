package com.hello.spiralworktask.view.login

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hello.spiralworktask.R
import kotlinx.android.synthetic.main.fragment_forgot_password.confirmFabButton
import kotlinx.android.synthetic.main.fragment_forgot_password.emailEditText
import kotlinx.android.synthetic.main.fragment_forgot_password.toolbar

class ForgotPasswordFragment : Fragment() {

  companion object {
    fun newInstance(): ForgotPasswordFragment = ForgotPasswordFragment()
  }

  private var listener: ForgotPasswordInteraction? = null

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? = inflater.inflate(R.layout.fragment_forgot_password, container, false)

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    toolbar.setNavigationOnClickListener { listener?.onBackButtonClicked() }
    confirmFabButton.setOnClickListener {
      listener?.onRequestPassword(
          emailEditText.text.toString()
      )
    }
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

  interface ForgotPasswordInteraction {
    fun onRequestPassword(email: String)
    fun onBackButtonClicked()
  }

}
