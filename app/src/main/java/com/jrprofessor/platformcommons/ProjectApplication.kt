package com.jrprofessor.platformcommons

import android.content.Context
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.util.Log
import androidx.work.Configuration
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ListenableWorker
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.jrprofessor.platformcommons.dagger.DaggerAppComponent
import com.jrprofessor.platformcommons.dagger.NetworkModule
import com.jrprofessor.platformcommons.repository.DatabaseRepository
import com.jrprofessor.platformcommons.workmanager.SyncWorker
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ProjectApplication : DaggerApplication(), Configuration.Provider {
    private val networkReceiver = NetworkChangeReceiver()
    override fun onCreate() {
        super.onCreate()
        val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(networkReceiver, filter)
    }

    @Inject
    lateinit var syncWorkerFactory: CustomWorkFactory

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setMinimumLoggingLevel(Log.DEBUG)
            .setWorkerFactory(syncWorkerFactory) // Ensure this is set
            .build()

    private val androidInjector = DaggerAppComponent.builder()
        .application(this)
        .networkModule(
            NetworkModule(
                "https://reqres.in/",
                "https://api.themoviedb.org/",
                debug = true
            )
        )
        .build()

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> = androidInjector

    override fun onTerminate() {
        super.onTerminate()
        unregisterReceiver(networkReceiver)
    }
}

class CustomWorkFactory @Inject constructor(private val databaseRepository: DatabaseRepository) :
    WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker = SyncWorker(appContext, workerParameters, databaseRepository)
}