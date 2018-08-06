package com.hello.spiralworktask.model

import com.google.gson.annotations.SerializedName

data class AccessToken(
  @SerializedName("token_type") val tokenType: String,
  @SerializedName("token") val token: String
) {

  val authorization: String get() = "$tokenType $token"
}