package com.hello.spiralworktask.view.welcome

import android.os.Bundle
import com.hello.spiralworktask.R
import com.hello.spiralworktask.libs.android.BaseActivity
import com.hello.spiralworktask.libs.session.UserManager
import javax.inject.Inject

class WelcomeUserActivity : BaseActivity() {

  @Inject lateinit var userManger: UserManager

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_welcome)

  }
}
