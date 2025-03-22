package com.jrprofessor.platformcommons

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.jrprofessor.platformcommons.workmanager.SyncWorker
import java.time.Duration

class NetworkChangeReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (context != null) {
            when (intent?.action) {
                ConnectivityManager.CONNECTIVITY_ACTION -> {
                    if (isNetworkAvailable(context)) {
                        Log.d("NetworkReceiver", "Network connected, scheduling WorkManager task.")
                        setOneTimeWorkRequest(context)
                    }
                }

                Intent.ACTION_BOOT_COMPLETED -> {
                    Log.d("NetworkReceiver", "Device rebooted, scheduling WorkManager.")
                    setOneTimeWorkRequest(context)
                }
            }
        }
    }

    private fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
        return networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
    }

    private fun setOneTimeWorkRequest(context: Context) {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        val workRequest = OneTimeWorkRequestBuilder<SyncWorker>()
            .setConstraints(constraints)
            .build()
        WorkManager.getInstance(context).enqueueUniqueWork(
            "SyncWorker",
            ExistingWorkPolicy.REPLACE,
            workRequest
        )
    }
}
