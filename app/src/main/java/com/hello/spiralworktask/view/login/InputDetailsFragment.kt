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
import kotlinx.android.synthetic.main.fragment_input_details.toolbar

class InputDetailsFragment : Fragment() {

  companion object {
    fun newInstance(): InputDetailsFragment = InputDetailsFragment()
  }

  private var listener: InputDetailsInteraction? = null

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? = inflater.inflate(R.layout.fragment_input_details, container, false)

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    toolbar.setNavigationOnClickListener { listener?.onBackButtonClicked() }
    confirmFabButton.setOnClickListener {
      listener?.onSubmitDetails(
          firstNameEditText.text.toString(), lastNameEditText.text.toString()
      )
    }
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

  interface InputDetailsInteraction {
    fun onSubmitDetails(
      firstName: String,
      lastName: String
    )

    fun onBackButtonClicked()
  }

}
