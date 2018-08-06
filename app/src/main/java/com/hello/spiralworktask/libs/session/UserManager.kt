package com.hello.spiralworktask.libs.session

import android.content.SharedPreferences
import com.google.gson.Gson
import com.hello.spiralworktask.libs.android.StringPreference
import com.hello.spiralworktask.model.Session

class UserManager(
  private val gson: Gson,
  sharedPreferences: SharedPreferences
) {

  companion object {
    private const val SESSION_PREF_KEY = "session-pref-key"
  }

  private var session: Session? = null
  private val sessionPreference = StringPreference(sharedPreferences, SESSION_PREF_KEY, null)

  fun saveSession(session: Session) {
    sessionPreference.set(gson.toJson(session))
  }

  fun startSession(): Boolean {
    val sessionString = sessionPreference.get()
    if (sessionString?.isNotEmpty() == true) {
      session = gson.fromJson(sessionString, Session::class.java)
      return true
    }
    return false
  }

  fun isUserSessionStartedOrStartSessionIfPossible(): Boolean {
    return session != null || startSession()
  }

  fun closeSession() {
    sessionPreference.delete()
    session = null
  }

  fun getSession(): Session? {
    return session
  }
}
