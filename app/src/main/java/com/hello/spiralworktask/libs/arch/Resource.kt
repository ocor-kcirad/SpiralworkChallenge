package com.hello.spiralworktask.libs.arch

import com.hello.spiralworktask.libs.arch.Status.ERROR
import com.hello.spiralworktask.libs.arch.Status.LOADING
import com.hello.spiralworktask.libs.arch.Status.SUCCESS

class Resource<out T> private constructor(
  val status: Status,
  val data: T? = null,
  val errorMessage: String? = null
) {

  companion object {
    fun <T> loading(data: T? = null): Resource<T> = Resource(LOADING, data, null)

    fun <T> success(data: T? = null): Resource<T> = Resource(SUCCESS, data, null)

    fun <T> error(
      message: String? = null,
      data: T? = null
    ): Resource<T> = Resource(ERROR, data, message)
  }
}

enum class Status {
  LOADING,
  SUCCESS,
  ERROR
}