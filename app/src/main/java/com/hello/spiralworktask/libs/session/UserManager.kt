package com.hello.spiralworktask.libs.session

import android.content.SharedPreferences
import android.util.Log
import com.google.gson.Gson
import com.hello.spiralworktask.libs.android.StringPreference
import com.hello.spiralworktask.libs.di.component.UserComponent
import com.hello.spiralworktask.libs.di.module.UserModule
import com.hello.spiralworktask.model.Session

class UserManager(
  private val gson: Gson,
  private val sharedPreferences: SharedPreferences,
  private val userComponentBuilder: UserComponent.Builder
) {

  companion object {
    private const val SESSION_PREF_KEY = "session-pref-key"
  }

  private var userComponent: UserComponent? = null
  private val sessionPreference = StringPreference(sharedPreferences, SESSION_PREF_KEY, null)

  fun saveSession(session: Session) {
    Log.d("Darick", "Saving Session")
    sessionPreference.set(gson.toJson(session))
  }

  fun startSession(): Boolean {

    Log.d("Darick", "Starting Session")
    val sessionString = sessionPreference.get()
    if (sessionString?.isNotEmpty() == true) {
      val session = gson.fromJson(sessionString, Session::class.java)
      userComponent = userComponentBuilder
          .sessionModule(UserModule(session))
          .build()
      return true
    }
    return false
  }

  fun isUserSessionStartedOrStartSessionIfPossible(): Boolean {
    Log.d("Darick", "Checking Session")
    return userComponent != null || startSession()
  }

  fun closeSession() {
    sessionPreference.delete()
    userComponent = null
  }

  fun getUserComponent(): UserComponent? {
    return userComponent
  }
}
