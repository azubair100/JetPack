package com.zubair.kotlinjetpack.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.zubair.kotlinjetpack.model.DogBreed
import com.zubair.kotlinjetpack.model.DogDataBase
import kotlinx.coroutines.launch

class DetailViewModel(application: Application) : BaseViewModel(application){

    val dogLiveData = MutableLiveData<DogBreed>()

    fun fetch(uuid: Int){
        launch {
            dogLiveData.value = DogDataBase(getApplication()).dogDAO().getDogById(uuid)
        }
    }

}