package com.hello.spiralworktask.view.welcome

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.hello.spiralworktask.libs.arch.BaseViewModel
import com.hello.spiralworktask.model.User
import com.hello.spiralworktask.usecase.CheckSessionUseCase
import com.hello.spiralworktask.usecase.LogoutUserUseCase
import com.hello.spiralworktask.usecase.ObtainUserUseCase
import io.reactivex.functions.Consumer
import javax.inject.Inject

class WelcomeUserViewModel @Inject constructor(
  private val checkSessionUseCase: CheckSessionUseCase,
  private val obtainUserUseCase: ObtainUserUseCase,
  private val logoutUserUseCase: LogoutUserUseCase
) : BaseViewModel() {

  val sessionState: LiveData<Boolean> get() = sessionStateLiveData
  val user: LiveData<User> get() = userLiveData

  private val sessionStateLiveData = MutableLiveData<Boolean>()
  private val userLiveData = MutableLiveData<User>()

  fun checkSession() =
    disposableContainer.add(
        checkSessionUseCase.check()
            .subscribe(
                { sessionStateLiveData.value = it },
                { sessionStateLiveData.value = false })
    )

  fun loadUser() {
    disposableContainer.add(
        obtainUserUseCase.obtain().subscribe(Consumer { userLiveData.value = it })
    )
  }

  fun logout() {
    disposableContainer.add(
        logoutUserUseCase.logout().subscribe { sessionStateLiveData.value = false }
    )
  }

}