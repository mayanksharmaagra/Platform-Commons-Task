package com.jrprofessor.platformcommons.roomdb

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {
    @Insert
    suspend fun insertEntry(entry: UserEntity)

    @Query("SELECT * FROM UserEntity WHERE isSynced = 0")
    fun getAllUnSyncUser(): List<UserEntity>

    @Query("SELECT * FROM UserEntity")
    fun getAllUser(): LiveData<List<UserEntity>>

    @Query("UPDATE UserEntity SET id = :newId,isSynced=1 WHERE id = :oldId")
    fun updateUserId(oldId: Int, newId: Int)
}