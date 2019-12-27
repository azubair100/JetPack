package com.zubair.kotlinjetpack.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zubair.kotlinjetpack.model.DogBreed

class DetailViewModel : ViewModel(){

    val dog = MutableLiveData<DogBreed>()

    fun fetch(){
        
    }

}