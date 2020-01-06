package com.zubair.kotlinjetpack.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.zubair.kotlinjetpack.model.DogBreed
import com.zubair.kotlinjetpack.model.DogDataBase
import kotlinx.coroutines.launch

class DetailViewModel(application: Application) : BaseViewModel(application){

    val dogLiveData : MutableLiveData<DogBreed> = MutableLiveData()

    fun fetch(uuid: Int){
        if(previousValuePresent()) {
            launch {
                dogLiveData.value = DogDataBase(getApplication()).dogDAO().getDogById(uuid)
                Toast.makeText(getApplication(), "Dogs gotten from database", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun previousValuePresent(): Boolean = dogLiveData.value == null

}