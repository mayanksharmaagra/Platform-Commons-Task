package com.jrprofessor.platformcommons.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.jrprofessor.platformcommons.network.ApiUserService
import com.jrprofessor.platformcommons.roomdb.UserDao
import com.jrprofessor.platformcommons.roomdb.UserEntity
import javax.inject.Inject

class DatabaseRepositoryImpl @Inject constructor(
    private val userDao: UserDao,
    private val userApi: ApiUserService,
) :
    DatabaseRepository {
    override suspend fun insertData(userEntity: UserEntity) {
        userDao.insertEntry(userEntity)
    }

    override fun getAllUnsyncedUsers(): List<UserEntity>{
        return userDao.getAllUnSyncUser()
    }

    override fun getAllUser(): LiveData<List<UserEntity>> {
        return userDao.getAllUser()
    }

    override fun updateUserId(oldId: Int, newId: Int) {
        userDao.updateUserId(oldId, newId)
    }

    override suspend fun syncUserDataToServer(unsyncedUsers: List<UserEntity>?) {
        unsyncedUsers?.forEach { user ->
            val body = hashMapOf(
                "name" to user.name,
                "job" to user.job
            )
            Log.e("WorkManagerStatus", "syncUserDataToServer: ")
            val response = userApi.saveUserData(body)
            if (response.isSuccessful) {
                userDao.updateUserId(user.id, response.body()?.id?.toInt() ?: user.id)
            }
        }
    }

    override suspend fun syncOfflineUsers(unsyncedUsers: List<UserEntity>) {
        unsyncedUsers.forEach { user ->
            try {
                val body = hashMapOf(
                    "name" to user.name,
                    "job" to user.job.trim()
                )
                Log.e("WorkManagerStatus", "syncUserDataToServer: ")
                val response = userApi.saveUserData(body)
                userDao.updateUserId(user.id, response.body()?.id?.toInt() ?: 100)
            } catch (e: Exception) {
                Log.e("TAG", "WorkManagerStatus:Error "+e.message )
                // Retry later
            }
        }
    }
}