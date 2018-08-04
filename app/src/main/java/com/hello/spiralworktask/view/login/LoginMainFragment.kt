package com.hello.spiralworktask.view.login

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hello.spiralworktask.R
import kotlinx.android.synthetic.main.fragment_login_main.createAccountTextView
import kotlinx.android.synthetic.main.fragment_login_main.loginTextView

class LoginMainFragment : Fragment() {

  companion object {
    fun newInstance(): LoginMainFragment = LoginMainFragment()
  }

  private var listener: LoginMainInteraction? = null

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? = inflater.inflate(R.layout.fragment_login_main, container, false)

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    loginTextView.setOnClickListener { listener?.onLoginClicked() }
    createAccountTextView.setOnClickListener { listener?.onCreateAccountClicked() }
  }

  override fun onAttach(context: Context?) {
    super.onAttach(context)
    if (context is LoginMainInteraction) {
      listener = context
    } else {
      throw RuntimeException(context!!.toString() + " must implement LoginMainInteraction")
    }
  }

  override fun onDetach() {
    super.onDetach()
    listener = null
  }

  interface LoginMainInteraction {
    fun onLoginClicked()
    fun onCreateAccountClicked()
  }

}
