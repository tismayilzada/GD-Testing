package com.griddynamics.gd_testing.data.remote.dto

import com.google.gson.annotations.SerializedName

data class OwnerDto(
    @SerializedName("login")
    val username: String? = null,
    @SerializedName("avatar_url")
    val avatar: String? = null
)