package com.hello.spiralworktask.model

import com.google.gson.annotations.SerializedName

data class Credentials(
  @SerializedName("email") var email: String,
  @SerializedName("password") var password: String
)