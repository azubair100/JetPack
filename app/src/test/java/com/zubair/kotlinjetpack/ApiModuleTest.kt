package com.zubair.kotlinjetpack

import com.zubair.kotlinjetpack.di.modules.ApiModule
import com.zubair.kotlinjetpack.network.DogService

class ApiModuleTest(val mockService: DogService): ApiModule() {
    override fun provideDogService(): DogService = mockService
}