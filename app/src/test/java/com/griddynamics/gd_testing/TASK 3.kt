package com.griddynamics.gd_testing

import com.griddynamics.gd_testing.common.Resource
import com.griddynamics.gd_testing.domain.repository.FakeGithubRepository
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class GithubRepositoryFakeTest {


    @Test
    fun `getUserRepositories returns success when username found`() {
        runBlocking {
            val userName = "GD"
            val repository = FakeGithubRepository()
            val result = repository.getUserRepositories(userName).drop(1).first().data
            assertThat(result).isNotEmpty
        }
    }

    @Test
    fun `getUserRepositories returns error when username not found`() {
        runBlocking {
            val userName = "GD21"
            val repository = FakeGithubRepository()
            val result = repository.getUserRepositories(userName).drop(1).first()
            assertThat(result).isInstanceOf(Resource.Error::class.java)
        }
    }
}