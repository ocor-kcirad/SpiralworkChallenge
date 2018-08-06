package com.hello.spiralworktask.view.login.emaillogin

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.hello.spiralworktask.libs.arch.BaseViewModel
import com.hello.spiralworktask.libs.ext.isValidEmail
import com.hello.spiralworktask.model.Credentials
import com.hello.spiralworktask.usecase.LoginUserUseCase
import com.hello.spiralworktask.view.login.emaillogin.EmailLoginViewModel.LoginState.Error
import com.hello.spiralworktask.view.login.emaillogin.EmailLoginViewModel.LoginState.Invalid
import com.hello.spiralworktask.view.login.emaillogin.EmailLoginViewModel.LoginState.LoggingIn
import com.hello.spiralworktask.view.login.emaillogin.EmailLoginViewModel.LoginState.Success
import com.hello.spiralworktask.view.login.emaillogin.EmailLoginViewModel.LoginState.Validated
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.processors.PublishProcessor
import io.reactivex.rxkotlin.Flowables
import javax.inject.Inject

class EmailLoginViewModel @Inject constructor(private val loginUserUseCase: LoginUserUseCase) :
    BaseViewModel() {

  val loginState: LiveData<LoginState> get() = loginStateLiveData

  private val loginStateLiveData = MutableLiveData<LoginState>()
  private val emailPublisher = PublishProcessor.create<CharSequence>()
  private val passwordPublisher = PublishProcessor.create<CharSequence>()

  var email: CharSequence? = null
    set(value) {
      if (field == null) {
        field = value
      }
      emailPublisher.onNext(email)
    }

  var password: CharSequence? = null
    set(value) {
      if (field == null) {
        field = value
      }

      Log.d("Darick", "Password: "+ password)
      passwordPublisher.onNext(password)
    }

  init {
    loginStateLiveData.value = Invalid
    disposableContainer.add(Flowables
        .combineLatest(emailPublisher, passwordPublisher, this::validateInputs)
        .map { if (it) Validated else Invalid }
        .distinctUntilChanged()
        .subscribe { loginStateLiveData.postValue(it) }
    )
  }

  fun submitLoginDetails() {
    loginStateLiveData.value = LoggingIn
    val credentials = Credentials(email!!.toString(), password!!.toString())
    disposableContainer.add(
        loginUserUseCase.post(credentials)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { loginStateLiveData.value = Success },
                { loginStateLiveData.value = Error })
    )
  }

  private fun validateInputs(
    email: CharSequence,
    password: CharSequence
  ): Boolean {
    val isEmailAcceptable = email.isNotEmpty() && email.isValidEmail()
    val isPasswordAcceptable = password.isNotEmpty()
    return isEmailAcceptable && isPasswordAcceptable
  }

  sealed class LoginState {
    object LoggingIn : LoginState()
    object Success : LoginState()
    object Validated : LoginState()
    object Invalid : LoginState()
    object Error : LoginState()
  }
}