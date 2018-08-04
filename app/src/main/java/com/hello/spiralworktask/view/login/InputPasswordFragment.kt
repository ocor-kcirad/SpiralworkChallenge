package com.hello.spiralworktask.view.login

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hello.spiralworktask.R
import kotlinx.android.synthetic.main.fragment_input_password.confirmFabButton
import kotlinx.android.synthetic.main.fragment_input_password.passwordEditText

class InputPasswordFragment : Fragment() {

  companion object {
    fun newInstance(): InputPasswordFragment = InputPasswordFragment()
  }

  private var listener: InputPasswordInteraction? = null

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? = inflater.inflate(R.layout.fragment_input_password, container, false)

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    confirmFabButton.setOnClickListener {
      listener?.onSubmitPassword(
          passwordEditText.text.toString()
      )
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

  interface InputPasswordInteraction {
    fun onSubmitPassword(password: String)
  }

}
