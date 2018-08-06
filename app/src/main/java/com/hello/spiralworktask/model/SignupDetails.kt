package com.hello.spiralworktask.model

import com.google.gson.annotations.SerializedName

data class SignupDetails(
  @SerializedName("email") val email: String,
  @SerializedName("password") val password: String,
  @SerializedName("first_name") val firstName: String,
  @SerializedName("last_name") val lastName: String
)