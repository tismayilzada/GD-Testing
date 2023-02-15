package com.griddynamics.gd_testing

import com.griddynamics.gd_testing.data.remote.dto.RepositoryDto
import com.griddynamics.gd_testing.data.remote.dto.toRepositoryModel
import org.junit.Assert
import org.junit.Test
import kotlin.random.Random

class MapperTest {


    @Test
    fun fields_isEquals() {
        val repositoryDto = RepositoryDto(Random.nextLong(), "test_name", "test_desc")
        val repositoryModel = repositoryDto.toRepositoryModel()
        Assert.assertEquals(repositoryDto.name,repositoryModel.name)
        Assert.assertEquals(repositoryDto.description,repositoryModel.description)
    }
}