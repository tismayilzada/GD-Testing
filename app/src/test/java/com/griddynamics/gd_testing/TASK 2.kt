package com.griddynamics.gd_testing

import com.griddynamics.gd_testing.data.remote.dto.RepositoryDto
import com.griddynamics.gd_testing.data.remote.dto.toRepositoryModel
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import kotlin.random.Random

class MapperTestWithAssertJ {


    @Test
    fun fields_isEquals() {
        val dto = RepositoryDto(Random.nextLong(), "test_name", "test_desc")
        val model = dto.toRepositoryModel()
        assertThat(dto.name).isEqualTo(model.name)
        assertThat(dto.description).isEqualTo(model.description)
    }
}