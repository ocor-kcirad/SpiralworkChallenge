package com.hello.spiralworktask.domain.usecase

import com.hello.spiralworktask.libs.net.APIService
import com.hello.spiralworktask.model.AccessToken
import com.hello.spiralworktask.model.Credentials
import io.reactivex.Single
import javax.inject.Inject

//TODO: Save token here...
class LoginUserUseCase @Inject constructor(private val apiService: APIService) {

  fun post(credentials: Credentials): Single<AccessToken> = apiService.login(credentials)

}