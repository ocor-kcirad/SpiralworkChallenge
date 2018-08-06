package com.hello.spiralworktask.view.login

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.hello.spiralworktask.usecase.CheckSessionUseCase
import com.hello.spiralworktask.usecase.StartSessionUseCase
import com.hello.spiralworktask.libs.arch.BaseViewModel
import com.hello.spiralworktask.view.login.LoginViewModel.SessionState.NoSession
import com.hello.spiralworktask.view.login.LoginViewModel.SessionState.Resumed
import com.hello.spiralworktask.view.login.LoginViewModel.SessionState.Started
import javax.inject.Inject

class LoginViewModel @Inject constructor(
  private val checkSessionUseCase: CheckSessionUseCase,
  private val startSessionUseCase: StartSessionUseCase
) : BaseViewModel() {

  val sessionState: LiveData<SessionState> get() = sessionStateLiveData

  private val sessionStateLiveData = MutableLiveData<SessionState>()

  init {
    sessionStateLiveData.value = NoSession
  }

  fun checkSession() =
    disposableContainer.add(checkSessionUseCase.check()
        .map { if (it) Resumed else NoSession }
        .subscribe(
            { sessionStateLiveData.value = it },
            { sessionStateLiveData.value = NoSession })
    )

  fun startSession() =
    disposableContainer.add(
        startSessionUseCase.start()
            .subscribe(
                { sessionStateLiveData.value = Started },
                { sessionStateLiveData.value = NoSession })
    )

  sealed class SessionState {
    object NoSession : SessionState()
    object Started : SessionState()
    object Resumed : SessionState()
  }

}