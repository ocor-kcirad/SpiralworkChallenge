package com.hello.spiralworktask.view.login

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hello.spiralworktask.R
import kotlinx.android.synthetic.main.fragment_input_details.confirmFabButton
import kotlinx.android.synthetic.main.fragment_input_details.firstNameEditText
import kotlinx.android.synthetic.main.fragment_input_details.lastNameEditText

class InputDetailsFragment : Fragment() {

  companion object {
    fun newInstance(): InputDetailsFragment = InputDetailsFragment()
  }

  private var mListener: InputDetailsInteraction? = null

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? = inflater.inflate(R.layout.fragment_input_details, container, false)

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    confirmFabButton.setOnClickListener {
      mListener?.onSubmitDetails(
          firstNameEditText.text.toString(), lastNameEditText.text.toString()
      )
    }
  }

  override fun onAttach(context: Context?) {
    super.onAttach(context)
    if (context is InputDetailsInteraction) {
      mListener = context
    } else {
      throw RuntimeException(context!!.toString() + " must implement InputDetailsInteraction")
    }
  }

  override fun onDetach() {
    super.onDetach()
    mListener = null
  }

  interface InputDetailsInteraction {
    fun onSubmitDetails(
      firstName: String,
      lastName: String
    )
  }

}
