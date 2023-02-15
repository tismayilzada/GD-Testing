package com.griddynamics.gd_testing.domain.repository

import com.griddynamics.gd_testing.common.Resource
import com.griddynamics.gd_testing.domain.model.CommitModel
import com.griddynamics.gd_testing.domain.model.RepositoryModel
import kotlinx.coroutines.flow.Flow

interface GithubRepository {

    suspend fun getUserRepositories(username: String): Flow<Resource<List<RepositoryModel>>>

    suspend fun getRepoCommits(username: String, repoName: String): Flow<Resource<List<CommitModel>>>

}