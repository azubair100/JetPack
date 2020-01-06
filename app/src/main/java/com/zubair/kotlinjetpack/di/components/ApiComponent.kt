package com.zubair.kotlinjetpack.di.components

import com.zubair.kotlinjetpack.di.modules.ApiModule
import com.zubair.kotlinjetpack.network.DogService
import dagger.Component

/*ApiComponent is a class for creating a connection between
    ApiModule and DogService to inject DogApi through creating DaggerApiComponent.
    DaggerApiComponent will call create().inject() method*/
@Component(modules = [ApiModule::class])
interface ApiComponent {
    //This tells the system DogService from ApiModule will be injected in DogService
    fun inject(service: DogService)
}