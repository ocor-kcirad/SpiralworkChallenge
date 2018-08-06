package com.hello.spiralworktask.domain.usecase

import com.hello.spiralworktask.libs.net.APIService
import com.hello.spiralworktask.model.AccessToken
import com.hello.spiralworktask.model.Credentials
import com.hello.spiralworktask.model.SignupDetails
import io.reactivex.Single
import javax.inject.Inject

//TODO: Save token here...
class LoginUserUseCase @Inject constructor(private val apiService: APIService) {

  fun post(credentials: Credentials): Single<AccessToken> = apiService.login(credentials)

}

class CreateUserUseCase @Inject constructor(private val apiService: APIService) {

  fun post(signupDetails: SignupDetails): Single<AccessToken> = apiService.signup(signupDetails)

}

class CheckEmailAvailabilityUseCase @Inject constructor(private val apiService: APIService) {

  fun post(email: String): Single<AccessToken> = apiService.checkEmail(email)

}