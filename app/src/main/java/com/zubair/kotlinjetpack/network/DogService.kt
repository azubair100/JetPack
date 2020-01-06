package com.zubair.kotlinjetpack.network

import com.zubair.kotlinjetpack.di.components.DaggerApiComponent
import com.zubair.kotlinjetpack.model.DogBreed
import io.reactivex.Single
import javax.inject.Inject

//Todo: Dagger Goal #2: Inject DogService into ListViewModel class
class DogService {
   /*Todo: Dagger Goal #1: Inject DogApi interface into DogService class
    Step 1: We created ApiModule.providesDogAPI() functionality
    Step 2: Then we created ApiComponent.inject(DogService)
    That ^^ tells us where ApiModule.providesDogAPI() will be injected*/
    @Inject
    lateinit var api : DogApi

    /*Step 3: Run clean project and rebuild project and add this line*/
    init { DaggerApiComponent.create().inject(this) }

    fun getDogs(): Single<List<DogBreed>> = api.getDogs()
}