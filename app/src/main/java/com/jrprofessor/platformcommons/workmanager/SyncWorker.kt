package com.jrprofessor.platformcommons.workmanager

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.google.gson.Gson
import com.jrprofessor.platformcommons.repository.DatabaseRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

class SyncWorker @AssistedInject constructor(
    @Assisted  context: Context,
    @Assisted workerParams: WorkerParameters,
    @Assisted var databaseRepository: DatabaseRepository
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            Log.d("SyncWorker", "Starting sync process...")

            val unsyncedUsers = databaseRepository.getAllUnsyncedUsers()
            Log.d("SyncWorker", "Sync data "+ Gson().toJson(unsyncedUsers))
            if (unsyncedUsers?.isNotEmpty() == true) {
                databaseRepository.syncOfflineUsers(unsyncedUsers)
            }
            Log.d("SyncWorker", "Sync completed successfully")
            Result.success()
        } catch (e: Exception) {
            Log.e("SyncWorker", "Error in SyncWorker: ${e.message}", e)
            Result.retry()
        }
    }
}
