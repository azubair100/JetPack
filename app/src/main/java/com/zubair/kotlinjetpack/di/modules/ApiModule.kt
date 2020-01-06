package com.zubair.kotlinjetpack.di.modules

import com.zubair.kotlinjetpack.network.DogApi
import com.zubair.kotlinjetpack.network.DogService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module //Modules have functionalities that we want to inject
class ApiModule {
    private val BASE_URL = "https://raw.githubusercontent.com/"

    /*Allows us to inject DogApi where ever we want to
    Basically we will provide a DogApi somewhere*/
    @Provides
    fun provideApi() : DogApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(provideOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(DogApi::class.java)
    }


    /*Allows us to inject DogService where ever we want to
    Basically we will provide a DogService somewhere*/
    @Provides
    fun provideDogService(): DogService = DogService()

    private fun provideOkHttpClient(): OkHttpClient {
        val interceptor =
            HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val okHttpClientBuilder = OkHttpClient.Builder()
        okHttpClientBuilder.connectTimeout(300, TimeUnit.SECONDS)
        okHttpClientBuilder.readTimeout(30, TimeUnit.SECONDS)
        okHttpClientBuilder.writeTimeout(30, TimeUnit.SECONDS)
        okHttpClientBuilder.addInterceptor(interceptor)
        return okHttpClientBuilder.build()
    }
}