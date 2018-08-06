package com.hello.spiralworktask.libs.android

import android.content.SharedPreferences

class StringPreference @JvmOverloads constructor(
  private val sharedPreferences: SharedPreferences,
  private val key: String,
  private val defaultValue: String? = null
) {

  val isSet: Boolean
    get() = this.sharedPreferences.contains(this.key)

  fun get(): String? {
    return this.sharedPreferences.getString(this.key, this.defaultValue)
  }

  fun set(value: String) {
    this.sharedPreferences.edit()
        .putString(this.key, value)
        .apply()
  }

  fun delete() {
    this.sharedPreferences.edit()
        .remove(this.key)
        .apply()
  }
}
