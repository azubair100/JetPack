package com.zubair.kotlinjetpack.di.components

import com.zubair.kotlinjetpack.di.modules.ApiModule
import com.zubair.kotlinjetpack.di.modules.AppModule
import com.zubair.kotlinjetpack.di.modules.PrefsModule
import com.zubair.kotlinjetpack.viewmodel.ListViewModel
import dagger.Component
import javax.inject.Singleton

/*  ViewModelComponent is a class for creating a connection between
    ApiModule, PrefsModule, AppModule in ListViewModel through DaggerViewModelComponent
    DaggerViewModelComponent will call create().inject() method
    AppModule is needed for PrefsModule and PrefsModule needed in ListViewModel
    @Singleton is here because PrefsModule is a Singleton
*/
@Singleton
@Component(modules = [ApiModule::class, PrefsModule::class, AppModule::class])
interface ViewModelComponent {
    /*This tells the system DogApi from ApiModule will be injected in DogService
    This tells the system SharedPreferencesHelper from PrefsModule will be injected in DogService
    This tells the system Application context from AppModule will be injected in DogService*/
    fun inject(viewModel: ListViewModel)
}