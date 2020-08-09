package com.zubair.kotlinjetpack

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Rule
import com.zubair.kotlinjetpack.di.components.DaggerViewModelComponent
import com.zubair.kotlinjetpack.di.modules.AppModule
import com.zubair.kotlinjetpack.model.DogBreed
import com.zubair.kotlinjetpack.network.DogService
import com.zubair.kotlinjetpack.util.SharedPreferencesHelper
import com.zubair.kotlinjetpack.viewmodel.ListViewModel
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.disposables.Disposable
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.plugins.RxJavaPlugins
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.util.concurrent.Executor
import java.util.concurrent.TimeUnit

class ListViewModelTest {

    @get:Rule
    var rule = InstantTaskExecutorRule()

    @Before
    fun setUpRxScheduler(){
        val immediate = object: Scheduler(){
            override fun scheduleDirect(run: Runnable, delay: Long, unit: TimeUnit): Disposable =
                super.scheduleDirect(run, 0, unit)

            override fun createWorker(): Worker =
                ExecutorScheduler.ExecutorWorker(Executor { it.run() }, true)
        }

        RxJavaPlugins.setInitIoSchedulerHandler { scheduler -> immediate }
        RxJavaPlugins.setInitComputationSchedulerHandler { scheduler -> immediate }
        RxJavaPlugins.setInitNewThreadSchedulerHandler { scheduler -> immediate }
        RxJavaPlugins.setInitSingleSchedulerHandler { scheduler -> immediate }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler() { scheduler -> immediate }

        //create mocks for modules since the list view model has a bunch
    }


    @Mock
    lateinit var dogService: DogService

    @Mock
    lateinit var prefs: SharedPreferencesHelper

    //Because we need the application right away
    val application = Mockito.mock(Application::class.java)
    var listViewModel = ListViewModel(application)

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
         DaggerViewModelComponent.builder()
            .appModule(AppModule(application))
            .apiModule(ApiModuleTest(dogService))
            .prefsModule(PrefModuleTest(prefs))
            .build()
            .inject(listViewModel)
    }


    @Test
    fun getDogsSuccess(){
        val dog = DogBreed("1", "TestBreed", "", "", "", "", "")
        val dogList = listOf(dog)
        val testSingle = Single.just(dogList)
        Mockito.`when`(dogService.getDogs()).thenReturn(testSingle)
        listViewModel.refresh()

        Assert.assertEquals(1, listViewModel.dogList.value?.size)
        Assert.assertEquals(false, listViewModel.listLLoadError.value)
        Assert.assertEquals(1, listViewModel.loading.value)
    }

}Odesksummer2014