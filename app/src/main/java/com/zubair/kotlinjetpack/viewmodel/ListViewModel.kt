package com.zubair.kotlinjetpack.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zubair.kotlinjetpack.model.DogBreed
import io.reactivex.disposables.CompositeDisposable

class ListViewModel : ViewModel() {

    val dogList = MutableLiveData<List<DogBreed>>()
    val listLLoadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()
    private val disposable = CompositeDisposable()


    fun refresh(){

    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

}