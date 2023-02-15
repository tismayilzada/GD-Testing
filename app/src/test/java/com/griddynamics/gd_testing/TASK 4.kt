package com.griddynamics.gd_testing

import com.griddynamics.gd_testing.common.Resource
import com.griddynamics.gd_testing.domain.model.RepositoryModel
import com.griddynamics.gd_testing.domain.repository.GithubRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class GithubRepositoryMockitoTest {


    @Mock
    private lateinit var repository: GithubRepository
    private lateinit var autoCloseable: AutoCloseable


    @Before
    fun setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this)
    }

    @After
    fun tearDown() {
        autoCloseable.close()
    }

    @Test
    fun `getUserRepositories returns success when username found`() {
        runBlocking {
            val mockData = listOf(mockRepositoryModel())
            val userName = "GD"

            `when`(repository.getUserRepositories(userName)).thenReturn(
                flowOf(Resource.Success(mockData))
            )

            val result = repository.getUserRepositories(userName).first()

            //Verify with assert
            assertEquals(result.data, mockData)

            //Verify with Mockito
            verify(repository).getUserRepositories(userName)
        }
    }


    @Test
    fun `getUserRepositories returns error when username not found`() {
        runBlocking {
            val userName = "GD 21"

            `when`(repository.getUserRepositories(userName)).thenReturn(
                flowOf(Resource.Error("No user found with this username."))
            )

            val result = repository.getUserRepositories(userName).first()

            //Verify with assert
            assertEquals(result.message, "No user found with this username.")

            //Verify with Mockito
            verify(repository).getUserRepositories(userName)
        }
    }

    private fun mockRepositoryModel() = RepositoryModel("", "", "")
}