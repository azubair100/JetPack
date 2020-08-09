package com.zubair.kotlinjetpack.network

import com.zubair.kotlinjetpack.model.DogBreed
import io.reactivex.Single
import retrofit2.http.GET

interface DogApi {
    @GET("DevTides/DogsApi/master/dogs.json")
//    @GET("planets/1/")
    fun getDogs() : Single<List<DogBreed>>

}