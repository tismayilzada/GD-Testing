package com.griddynamics.gd_testing.domain.repository

import com.griddynamics.gd_testing.common.Resource
import com.griddynamics.gd_testing.data.remote.dto.toCommitModel
import com.griddynamics.gd_testing.domain.model.CommitModel
import com.griddynamics.gd_testing.domain.model.RepositoryModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class FakeGithubRepository : GithubRepository {

    private val repositories = mutableListOf(
        RepositoryModel("Test", "Test description", "GD"),
        RepositoryModel("Test", "Test description", "GD"),
        RepositoryModel("Test", "Test description", "GD"),
        RepositoryModel("Test", "Test description", "GD1"),
        RepositoryModel("Test", "Test description", "GD1"),
        RepositoryModel("Test", "Test description", "GD1"),
        RepositoryModel("Test", "Test description", "GD2"),
        RepositoryModel("Test", "Test description", "GD2"),
        RepositoryModel("Test", "Test description", "GD2")
    )

    private val commits = mutableListOf(
        CommitModel("Test", "Test description", "GD"),
        CommitModel("Test", "Test description", "GD"),
        CommitModel("Test", "Test description", "GD"),
        CommitModel("Test", "Test description", "GD1"),
        CommitModel("Test", "Test description", "GD1"),
        CommitModel("Test", "Test description", "GD1"),
        CommitModel("Test", "Test description", "GD2"),
        CommitModel("Test", "Test description", "GD2"),
        CommitModel("Test", "Test description", "GD2")
    )

    override suspend fun getUserRepositories(username: String): Flow<Resource<List<RepositoryModel>>> =
        flow {
            emit(Resource.Loading())
            val isUserExists = repositories.any { it.ownerName == username }
            if (isUserExists) {
                emit(Resource.Success(repositories.filter { it.ownerName == username }))
            } else {
                emit(Resource.Error("No user found with this username."))
            }
        }



    override suspend fun getRepoCommits(
        username: String,
        repoName: String
    ): Flow<Resource<List<CommitModel>>> =
        flow {
            emit(Resource.Loading())
            val hasRepoCommits = commits.any { it.committerName == username }
            if (hasRepoCommits) {
                emit(Resource.Success(commits.filter { it.committerName == username }))
            } else {
                emit(Resource.Error("This repo has no commits."))
            }
        }
}