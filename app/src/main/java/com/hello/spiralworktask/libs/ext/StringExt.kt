package com.hello.spiralworktask.libs.ext

fun CharSequence.isValidEmail() = android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()