package com.griddynamics.gd_testing.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.griddynamics.gd_testing.data.local.dto.RepoDto

@Database(
    entities = [RepoDto::class],
    version = 1,
    exportSchema = false
)
abstract class RepoDatabase : RoomDatabase() {

    abstract val repoDao: RepoDao

    companion object {
        const val DATABASE_NAME = "repo_db"
    }
}