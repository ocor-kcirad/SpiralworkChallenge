package com.hello.spiralworktask.libs.ext

import android.support.design.widget.Snackbar
import android.text.Html
import android.view.View

inline fun View.snackError(
  error: String,
  message: String,
  length: Int = Snackbar.LENGTH_LONG,
  f: Snackbar.() -> Unit
) {
  val snack = Snackbar.make(
      this, Html.fromHtml(
      "<b><font color=\"#FF4081\">$error: </font></b> <font color=\"#37474F\">$message</font>"
  ), length
  )
  snack.f()
  snack.show()
}