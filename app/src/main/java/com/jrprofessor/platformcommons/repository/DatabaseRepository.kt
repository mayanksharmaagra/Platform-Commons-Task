package com.jrprofessor.platformcommons.repository

import androidx.lifecycle.LiveData
import com.jrprofessor.platformcommons.roomdb.UserEntity

interface DatabaseRepository {
    suspend fun insertData(userDao: UserEntity)
    fun getAllUnsyncedUsers():List<UserEntity>
    fun getAllUser():LiveData<List<UserEntity>>
    fun updateUserId(oldId:Int,newId:Int)
    suspend fun syncUserDataToServer(unsyncedUsers: List<UserEntity>?)
    suspend fun syncOfflineUsers(unsyncedUsers: List<UserEntity>)
}