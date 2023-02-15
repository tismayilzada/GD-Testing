package com.griddynamics.gd_testing.data.local

import androidx.room.*
import com.griddynamics.gd_testing.data.local.dto.RepoDto
import kotlinx.coroutines.flow.Flow

@Dao
interface RepoDao {

    @Query("SELECT * FROM repo")
    fun getRepos(): Flow<List<RepoDto>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRepo(repo: RepoDto)

    @Delete
    suspend fun deleteRepo(repo: RepoDto)
}