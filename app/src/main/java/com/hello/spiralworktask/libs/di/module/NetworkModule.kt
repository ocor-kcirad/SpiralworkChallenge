package com.hello.spiralworktask.libs.di.module

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.hello.spiralworktask.libs.di.scopes.PerApplication
import com.hello.spiralworktask.libs.net.APIService
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import okhttp3.Call
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Named

@Module
class NetworkModule {

  @Provides
  @Named("endpoint")
  fun provideEndpoint() = "http://192.168.1.176:3000/"

  @PerApplication
  @Provides
  fun provideRestService(retrofit: Retrofit): APIService = retrofit.create(APIService::class.java)

  @Provides
  fun provideRetrofit(
    @Named("endpoint") endpoint: String,
    gson: Gson,
    callFactory: Call.Factory
  ): Retrofit =
    Retrofit.Builder()
        .callFactory(callFactory)
        .baseUrl(endpoint)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
        .build()

  @Provides
  fun provideOkHttpCallFactory(): Call.Factory = OkHttpClient
      .Builder()
      .build()

  @PerApplication
  @Provides
  fun provideGson(): Gson = GsonBuilder().create()
}