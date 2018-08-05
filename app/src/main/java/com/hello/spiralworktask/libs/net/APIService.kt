package com.hello.spiralworktask.libs.net

import com.hello.spiralworktask.model.AccessToken
import com.hello.spiralworktask.model.SignupDetails
import com.hello.spiralworktask.model.User
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface APIService {

  @Multipart
  @POST("api/login")
  fun login(
    @Part("email") email: String,
    @Part("password") password: String
  ): Single<AccessToken>

  @Multipart
  @POST("api/signup")
  fun signup(@Body signupDetails: SignupDetails): Single<AccessToken>

  @POST("api/logout")
  fun logout(@Field("token") token: String): Completable

  @GET("api/me")
  fun me(@Header("Authorization") credentials: String): Single<User>

}