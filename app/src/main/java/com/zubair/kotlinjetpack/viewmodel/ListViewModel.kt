package com.zubair.kotlinjetpack.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.zubair.kotlinjetpack.di.components.DaggerViewModelComponent
import com.zubair.kotlinjetpack.di.modules.AppModule
import com.zubair.kotlinjetpack.di.modules.CONTEXT_APP
import com.zubair.kotlinjetpack.di.modules.TypeOfContext
import com.zubair.kotlinjetpack.model.DogBreed
import com.zubair.kotlinjetpack.model.DogDataBase
import com.zubair.kotlinjetpack.network.DogService
import com.zubair.kotlinjetpack.util.NotificationsHelper
import com.zubair.kotlinjetpack.util.SharedPreferencesHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import java.lang.NumberFormatException
import javax.inject.Inject

class ListViewModel(application: Application) : BaseViewModel(application) {

    //Basic operational variables
    var dogList: MutableLiveData<List<DogBreed>> = MutableLiveData()
    val listLLoadError: MutableLiveData<Boolean> = MutableLiveData()
    val loading: MutableLiveData<Boolean> = MutableLiveData()
    private val disposable = CompositeDisposable()
    //time difference is 5 minutes + in nano seconds in Long
    private var refreshTime = 500 * 1000 * 1000 * 1000L

   /*We can mock these two objects during unit testing
    Inject at runtime and mock it during unit testing
    For keeping track of the last room database, dogs table update*/

    /*Todo: Dagger Goal #3: Inject SharedPreferencesHelper into ListViewModel class and mock in Unit Test
    This one is a little complex because SharedPreferencesHelper takes in application context
    Step 1: Add provideSharedPref in PrefsModule (Check Goal #1 in DogService)
    Step 2: As a requirement of an Application object inside SharedPreferencesHelper create AppModule
    Step 3: Add the modules in the ViewModelComponent and make the class a Singleton*/

    /*Todo: Dagger Goal #4: @field: TypeOfContext(CONTEXT_APP) from PrefModules @Qualifier*/
    @Inject
    @field: TypeOfContext(CONTEXT_APP)
    lateinit var prefHelper: SharedPreferencesHelper

    /*Todo: Dagger Goal #2: Inject DogService into ListViewModel class and mock in Unit Test
    Step 1: Add provideDogService in ApiModule (Check Goal #1 in DogService)
    Step 2: Create ViewModelComponent add the ApiModule in the "modules =()"
    Step 3: The commented ********* code init{}"*/
    @Inject lateinit var dogService: DogService
/*
    without ListViewModelTest
    init{
    *//*
    This step only includes injecting DogService into ListViewModel
    *********DaggerViewModelComponent.create().inject(this)*********

    We can not use the code above once we decide to inject SharedPreferencesHelper because
    PrefModule needs an AppModule &
    AppModule needs an Application object as param as a Singleton
    *//*
    DaggerViewModelComponent.builder()
       .appModule(AppModule(application))
       .build().inject(this)
    }*/


    //supporting test
    private var injected = false
    constructor(application: Application, test: Boolean = true): this(application){
        injected = true
    }

    fun inject(){
        if(!injected){
            DaggerViewModelComponent.builder()
                .appModule(AppModule(getApplication()))
                .build().inject(this)
        }
    }

    //supporting test


    fun refresh(){
        inject()
//        if(!previousValuePresent()) {
           checkCacheDuration()
           val updateTime = prefHelper.getUpdateTime()
           if (updateTime != null && updateTime != 0L && System.nanoTime() - updateTime < refreshTime) {
               fetchFromDatabase()
           }
           else { fetchFromRemote() }
//        }
    }

    private fun previousValuePresent(): Boolean = !dogList.value.isNullOrEmpty()

    fun refreshByPassCache(){ fetchFromRemote() }

    private fun checkCacheDuration(){
        val cachePreference = prefHelper.getCachedDuration()
        try{
           val cachePreferenceInt = cachePreference?.toInt() ?: 500
           refreshTime = cachePreferenceInt.times(1000 * 1000 * 1000L)
        }
        catch (e: NumberFormatException){ e.printStackTrace() }
    }

    private fun fetchFromDatabase(){
        loading.value = true
        launch {
           dogsRetrieved(DogDataBase(getApplication()).dogDAO().getAllDogs())
           Toast.makeText(getApplication(), "Dogs gotten from database", Toast.LENGTH_LONG).show()
        }
    }

    //First get dogs from remote api and then store locally and then retrieve it
    private fun fetchFromRemote(){
        inject()
        loading.value = true
        disposable.add(
           dogService.getDogs()
               .subscribeOn(Schedulers.newThread())
               .observeOn(AndroidSchedulers.mainThread())
               .subscribeWith(object : DisposableSingleObserver<List<DogBreed>>(){
                   override fun onSuccess(dogs: List<DogBreed>) {
                       storeDogsLocally(dogs)
                       Toast.makeText(getApplication(),
                           "Dogs gotten from endpoint",
                           Toast.LENGTH_LONG).show()
                       NotificationsHelper(getApplication()).createNotification()
                   }

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
    * Store the time of when we had retrieved the dogs information remote;
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