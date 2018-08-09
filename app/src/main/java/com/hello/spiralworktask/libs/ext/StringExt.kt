package com.hello.spiralworktask.libs.ext

fun CharSequence.isValidEmail() = android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()

fun CharSequence.isValidPassword() =
  "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}\$".toRegex().containsMatchIn(this)