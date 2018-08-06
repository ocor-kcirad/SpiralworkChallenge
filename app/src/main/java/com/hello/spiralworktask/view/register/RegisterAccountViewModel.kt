package com.hello.spiralworktask.view.register

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.hello.spiralworktask.usecase.CheckEmailAvailabilityUseCase
import com.hello.spiralworktask.usecase.CreateUserUseCase
import com.hello.spiralworktask.libs.arch.BaseViewModel
import com.hello.spiralworktask.libs.arch.Resource
import com.hello.spiralworktask.libs.ext.isValidEmail
import com.hello.spiralworktask.libs.ext.isValidPassword
import com.hello.spiralworktask.model.AccessToken
import com.hello.spiralworktask.model.SignupDetails
import com.hello.spiralworktask.view.register.RegisterAccountViewModel.EmailInputState.EmailAccepted
import com.hello.spiralworktask.view.register.RegisterAccountViewModel.EmailInputState.ErrorEmailVerification
import com.hello.spiralworktask.view.register.RegisterAccountViewModel.EmailInputState.InvalidEmail
import com.hello.spiralworktask.view.register.RegisterAccountViewModel.PasswordInputState.InvalidPassword
import com.hello.spiralworktask.view.register.RegisterAccountViewModel.PasswordInputState.PasswordAccepted
import com.hello.spiralworktask.view.register.RegisterAccountViewModel.UserInputState.InvalidUserDetail
import com.hello.spiralworktask.view.register.RegisterAccountViewModel.UserInputState.UserDetailAccepted
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.processors.PublishProcessor
import io.reactivex.rxkotlin.Flowables
import javax.inject.Inject

class RegisterAccountViewModel @Inject constructor(
  private val createUserUseCase: CreateUserUseCase,
  private val checkEmailAvailabilityUseCase: CheckEmailAvailabilityUseCase
) :
    BaseViewModel() {

  val accountCreation: LiveData<Resource<AccessToken>> get() = accountCreationLiveData
  val userInputState: LiveData<UserInputState> get() = userInputStateLiveData
  val emailInputState: LiveData<EmailInputState> get() = emailInputStateLiveData
  val passwordInputState: LiveData<PasswordInputState> get() = passwordInputStateLiveData

  private val accountCreationLiveData = MutableLiveData<Resource<AccessToken>>()
  private val userInputStateLiveData = MutableLiveData<UserInputState>()
  private val emailInputStateLiveData = MutableLiveData<EmailInputState>()
  private val passwordInputStateLiveData = MutableLiveData<PasswordInputState>()
  private val emailPublisher = PublishProcessor.create<CharSequence>()
  private val passwordPublisher = PublishProcessor.create<CharSequence>()
  private val firstNamePublisher = PublishProcessor.create<CharSequence>()
  private val lastNamePublisher = PublishProcessor.create<CharSequence>()

  var firstName: CharSequence? = null
    set(value) {
      if (field == null) {
        field = value
      }
      firstNamePublisher.onNext(value)
    }

  var lastName: CharSequence? = null
    set(value) {
      if (field == null) {
        field = value
      }
      lastNamePublisher.onNext(value)
    }

  var email: CharSequence? = null
    set(value) {
      if (field == null) {
        field = value
      }
      emailPublisher.onNext(value)
    }

  var password: CharSequence? = null
    set(value) {
      if (field == null) {
        field = value
      }
      passwordPublisher.onNext(value)
    }

  init {

    userInputStateLiveData.value = InvalidUserDetail
    emailInputStateLiveData.value = InvalidEmail
    passwordInputStateLiveData.value = InvalidPassword

    val maybeValidEmail = Flowable.fromPublisher(emailPublisher)
        .filter { it.isValidEmail() }
        .flatMapSingle {
          checkEmailAvailabilityUseCase
              .post(it.toString())
              .map<EmailInputState> { EmailAccepted }
              .onErrorReturn {
                Log.d("Darick", "Error Type" + it)
                ErrorEmailVerification("Email is already taken")
              }
        }

    val invalidEmail = Flowable
        .fromPublisher(emailPublisher)
        .filter { !it.isValidEmail() }
        .map { InvalidEmail }

    disposableContainer.add(Flowable.merge(maybeValidEmail, invalidEmail)
        .distinctUntilChanged()
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe { emailInputStateLiveData.value = it }
    )

    disposableContainer.add(Flowables
        .combineLatest(firstNamePublisher, lastNamePublisher, this::validateUserDetailInputs)
        .map { if (it) UserDetailAccepted else InvalidUserDetail }
        .subscribe { userInputStateLiveData.value = it }
    )

    disposableContainer.add(Flowable.fromPublisher(passwordPublisher)
        .map { if (it.isValidPassword()) PasswordAccepted else InvalidPassword }
        .distinctUntilChanged()
        .subscribe { passwordInputStateLiveData.value = it })
  }

  fun createAccount() {
    accountCreationLiveData.value = Resource.loading()

    val signupDetails = SignupDetails(
        email.toString(),
        password.toString(),
        firstName.toString(),
        lastName.toString()
    )

    disposableContainer.add(
        createUserUseCase.post(signupDetails)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ accountCreationLiveData.value = Resource.success() },
                {
                  accountCreationLiveData.value =
                      Resource.error("Something went wrong. Please try again.")
                })
    )
  }

  private fun validateUserDetailInputs(
    firstName: CharSequence,
    lastName: CharSequence
  ): Boolean = firstName.isNotEmpty() && lastName.isNotEmpty()

  sealed class UserInputState {
    object UserDetailAccepted : UserInputState()
    object InvalidUserDetail : UserInputState()
  }

  sealed class EmailInputState {
    object EmailAccepted : EmailInputState()
    class ErrorEmailVerification(val error: String) : EmailInputState()
    object InvalidEmail : EmailInputState()
  }

  sealed class PasswordInputState {
    object PasswordAccepted : PasswordInputState()
    object InvalidPassword : PasswordInputState()
  }

}