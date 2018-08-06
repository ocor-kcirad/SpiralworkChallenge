package com.hello.spiralworktask.libs.net

import com.hello.spiralworktask.model.AccessToken
import com.hello.spiralworktask.model.Credentials
import com.hello.spiralworktask.model.SignupDetails
import com.hello.spiralworktask.model.User
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface APIService {

  @POST("api/login")
  fun login(@Body credentials: Credentials): Single<AccessToken>

  @POST("api/signup")
  fun signup(@Body signupDetails: SignupDetails): Single<AccessToken>

  @GET("api/check-email")
  fun checkEmail(@Query("email") email: String): Single<AccessToken>

  @FormUrlEncoded
  @POST("api/logout")
  fun logout(@Field("token") token: String): Completable

  @GET("api/me")
  fun me(@Header("Authorization") credentials: String): Single<User>

}