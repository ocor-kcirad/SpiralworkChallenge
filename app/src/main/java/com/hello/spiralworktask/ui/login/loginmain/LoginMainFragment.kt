package com.hello.spiralworktask.view.login.loginmain

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hello.spiralworktask.R
import com.hello.spiralworktask.libs.android.BaseFragment
import com.jakewharton.rxbinding2.support.v7.widget.RxToolbar
import com.jakewharton.rxbinding2.view.RxView
import kotlinx.android.synthetic.main.fragment_login_main.createAccountTextView
import kotlinx.android.synthetic.main.fragment_login_main.loginTextView
import kotlinx.android.synthetic.main.fragment_login_main.toolbar
import javax.inject.Inject

class LoginMainFragment : BaseFragment() {

  companion object {
    fun newInstance(): LoginMainFragment =
      LoginMainFragment()
  }

  interface LoginMainInteraction {
    fun onLoginClicked()
    fun onCreateAccountClicked()
    fun onCloseButtonClicked()
  }

  private var listener: LoginMainInteraction? = null

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? = inflater.inflate(R.layout.fragment_login_main, container, false)

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    subscribeToObservables()
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

  private fun subscribeToObservables() {
    disposableContainer.add(RxView.clicks(loginTextView)
        .subscribe { listener?.onLoginClicked() })
    disposableContainer.add(RxView.clicks(createAccountTextView)
        .subscribe { listener?.onCreateAccountClicked() })
    disposableContainer.add(RxToolbar.navigationClicks(toolbar)
        .subscribe { listener?.onCloseButtonClicked() })
  }

}
