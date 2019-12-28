package com.zubair.kotlinjetpack.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zubair.kotlinjetpack.model.DogBreed
import com.zubair.kotlinjetpack.model.DogDataBase
import com.zubair.kotlinjetpack.network.DogService
import com.zubair.kotlinjetpack.util.SharedPreferencesHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class ListViewModel(application: Application) : BaseViewModel(application) {

    val dogList = MutableLiveData<List<DogBreed>>()
    val listLLoadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()
    private val disposable = CompositeDisposable()
    private var prefHelper = SharedPreferencesHelper(getApplication())


    private val dogService = DogService()


    fun refresh(){ fetchFromRemote() }

    //First get dogs from remote api and then store locally and then retrieve it
    private fun fetchFromRemote(){
        loading.value = true
        disposable.add(
            dogService.getDogs()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<DogBreed>>(){
                    override fun onSuccess(dogs: List<DogBreed>) { storeDogsLocally(dogs) }

                    override fun onError(e: Throwable) {
                        listLLoadError.value = true
                        loading.value = false
                    }
                })
        )
    }

    private fun dogsRetrieved(dogs: List<DogBreed>){
        dogList.value = dogs
        listLLoadError.value = false
        loading.value = false
    }
/*
* Store the time of when we had retrieved the dogs information remote;y
* The reason for that is when we have the timestamp we can decide whether we need to re fetch the
* the information from the api or we can just call local database, room etc
* We also have the shared preference key value pair that stores exactly the time it was updated
*/
    private fun storeDogsLocally(list: List<DogBreed>){
        //Kotlin Co Routine, separate thread
        launch {
            val dao = DogDataBase(getApplication()).dogDAO()
            //We don't want old records, so delete everything from past
            dao.deleteAllDogs()
            /*It gets the list param and expands it into individual elements that's passed to
            varargs in DogDAO's insertAll() function*/
            val result = dao.insertAll(*list.toTypedArray())
            //Now you have to assign those List of dog uuids to the right objects
            var index = 0
            while(index < list.size){
                list[index].uuid = result[index].toInt()
                ++index
            }
            dogsRetrieved(list)
        }
        //Stores the exact time when the data was stored in room
        prefHelper.saveUpdateTime(System.nanoTime())
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

}