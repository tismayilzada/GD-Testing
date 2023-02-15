package com.griddynamics.gd_testing.data.remote

import com.griddynamics.gd_testing.data.remote.dto.CommitDto
import com.griddynamics.gd_testing.data.remote.dto.RepositoryDto
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface GithubAPI {

    @Headers("Accept: application/vnd.github+json", "X-GitHub-Api-Version: 2022-11-28")
    @GET("users/{username}/repos")
    suspend fun getRepos(
        @Path("username") username: String,
    ): List<RepositoryDto>

    @Headers("Accept: application/vnd.github+json", "X-GitHub-Api-Version: 2022-11-28")
    @GET("repos/{owner}/{repo}/commits")
    suspend fun getRepoCommits(
        @Path("owner") username: String,
        @Path("repo") repo: String,
    ): List<CommitDto>

}