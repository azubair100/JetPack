package com.zubair.kotlinjetpack.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

//This is for the database since it needs an application context
//And this is also done to do all the database work from the background
abstract class BaseViewModel(application: Application): AndroidViewModel(application), CoroutineScope {

    private val job = Job()

    //Basically we will have a job that's running in the background
    //And when it finishes we're going to return to our main thread
    override val coroutineContext: CoroutineContext get() = job + Dispatchers.Main

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}