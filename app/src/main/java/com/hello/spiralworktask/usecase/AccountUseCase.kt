package com.hello.spiralworktask.usecase

import com.hello.spiralworktask.libs.net.APIService
import com.hello.spiralworktask.libs.session.UserManager
import com.hello.spiralworktask.model.AccessToken
import com.hello.spiralworktask.model.Credentials
import com.hello.spiralworktask.model.Session
import com.hello.spiralworktask.model.SignupDetails
import com.hello.spiralworktask.model.User
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class LoginUserUseCase @Inject constructor(
  private val apiService: APIService,
  private val userManager: UserManager
) {

  fun post(credentials: Credentials): Completable = apiService
      .login(credentials)
      .flatMap { token ->
        apiService.me(token.authorization)
            .map { user -> Session(token, user) }
            .doOnSuccess { userManager.saveSession(it) }
            .doOnSuccess { userManager.startSession() }

      }.ignoreElement()

}

class CreateUserUseCase @Inject constructor(
  private val apiService: APIService,
  private val userManager: UserManager
) {

  fun post(signupDetails: SignupDetails): Completable = apiService
      .signup(signupDetails)
      .flatMap { token ->
        apiService.me(token.authorization)
            .map { user -> Session(token, user) }
            .doOnSuccess { userManager.saveSession(it) }
            .doOnSuccess { userManager.startSession() }

      }.ignoreElement()

}

class CheckEmailAvailabilityUseCase @Inject constructor(private val apiService: APIService) {

  fun post(email: String): Single<AccessToken> = apiService
      .checkEmail(email)

}

class CheckSessionUseCase @Inject constructor(private val userManager: UserManager) {

  fun check(): Single<Boolean> = Single
      .just(userManager.isUserSessionStartedOrStartSessionIfPossible())

}

class StartSessionUseCase @Inject constructor(
  private val userManager: UserManager
) {

  fun start(): Completable = Single
      .just(userManager.startSession())
      .filter { it }
      .ignoreElement()

}

class ObtainUserUseCase @Inject constructor(private val userManager: UserManager) {

  fun obtain(): Single<User> = Single
      .just(userManager.getSession())
      .map { it.user }

}

class LogoutUserUseCase @Inject constructor(private val userManager: UserManager) {

  fun logout(): Completable = Completable.fromAction { userManager.closeSession() }
}