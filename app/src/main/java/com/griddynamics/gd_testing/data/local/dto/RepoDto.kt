package com.griddynamics.gd_testing.data.local.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "repo")
data class RepoDto(
    @PrimaryKey
    val id: Int? = null,
    val name: String
)
