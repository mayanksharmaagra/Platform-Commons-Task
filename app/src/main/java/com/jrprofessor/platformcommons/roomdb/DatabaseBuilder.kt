package com.jrprofessor.platformcommons.roomdb

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [UserEntity::class], version = 1, exportSchema = false)
abstract class DatabaseBuilder : RoomDatabase() {
    abstract fun getDatabase(): UserDao
}