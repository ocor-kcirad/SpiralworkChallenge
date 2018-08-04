package com.hello.spiralworktask.view.login

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hello.spiralworktask.R
import kotlinx.android.synthetic.main.fragment_email_login.forgotPasswordTextView
import kotlinx.android.synthetic.main.fragment_email_login.showPasswordTextView
import kotlinx.android.synthetic.main.fragment_email_login.toolbar

class EmailLoginFragment : Fragment() {

  companion object {
    fun newInstance(): EmailLoginFragment = EmailLoginFragment()
  }

  private var listener: EmailLoginInteraction? = null

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? = inflater.inflate(R.layout.fragment_email_login, container, false)

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    toolbar.setNavigationOnClickListener { listener?.onBackButtonClicked()}
    forgotPasswordTextView.setOnClickListener { listener?.onForgotPasswordClicked() }
    showPasswordTextView.setOnClickListener {
      showPasswordTextView.showNext()
    }
  }

  override fun onAttach(context: Context?) {
    super.onAttach(context)
    if (context is EmailLoginInteraction) {
      listener = context
    } else {
      throw RuntimeException(context!!.toString() + " must implement EmailLoginInteraction")
    }
  }

  override fun onDetach() {
    super.onDetach()
    listener = null
  }

  interface EmailLoginInteraction {
    fun onForgotPasswordClicked()
    fun onBackButtonClicked()
  }

}
