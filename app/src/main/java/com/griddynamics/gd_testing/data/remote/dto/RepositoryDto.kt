package com.griddynamics.gd_testing.data.remote.dto


import com.google.gson.annotations.SerializedName
import com.griddynamics.gd_testing.domain.model.RepositoryModel

data class RepositoryDto(
    val id: Long? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("owner")
    val owner: OwnerDto? = null
)

fun RepositoryDto.toRepositoryModel(): RepositoryModel {
    return RepositoryModel(name = name ?: "", description = description ?: "", ownerName = owner?.username ?: "")
}