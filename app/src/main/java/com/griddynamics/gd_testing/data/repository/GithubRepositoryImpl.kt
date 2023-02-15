package com.griddynamics.gd_testing.data.repository

import com.griddynamics.gd_testing.common.Resource
import com.griddynamics.gd_testing.data.remote.GithubAPI
import com.griddynamics.gd_testing.data.remote.dto.CommitDto
import com.griddynamics.gd_testing.data.remote.dto.toCommitModel
import com.griddynamics.gd_testing.data.remote.dto.toRepositoryModel
import com.griddynamics.gd_testing.domain.model.CommitModel
import com.griddynamics.gd_testing.domain.model.RepositoryModel
import com.griddynamics.gd_testing.domain.repository.GithubRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class GithubRepositoryImpl(private val api: GithubAPI) : GithubRepository {

    override suspend fun getUserRepositories(username: String): Flow<Resource<List<RepositoryModel>>> =
        flow {
            try {
                emit(Resource.Loading())
                val repos = api.getRepos(username = username).map { it.toRepositoryModel() }
                if (repos.isEmpty()) {
                    emit(Resource.Error("This user has no repositories."))
                    return@flow
                }
                emit(Resource.Success(repos))
            } catch (e: HttpException) {
                if (e.code() == 404) {
                    emit(Resource.Error("No user found with this username."))
                } else {
                    emit(Resource.Error(e.localizedMessage ?: "An unexpected error occured"))
                }
            } catch (e: IOException) {
                emit(Resource.Error("Couldn't reach server. Check your internet connection."))
            }
        }

    override suspend fun getRepoCommits(
        username: String,
        repoName: String
    ): Flow<Resource<List<CommitModel>>> = flow {
        try {
            emit(Resource.Loading())
            val commits =
                api.getRepoCommits(username = username, repo = repoName).map { it.toCommitModel() }
            if (commits.isEmpty()) {
                emit(Resource.Error("This repo has no commits."))
                return@flow
            }
            emit(Resource.Success(commits))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occured"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }

}