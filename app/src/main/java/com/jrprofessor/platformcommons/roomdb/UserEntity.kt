package com.jrprofessor.platformcommons.roomdb

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "job") val job: String,
    @ColumnInfo(name = "isSynced")val isSynced: Boolean = false,
)