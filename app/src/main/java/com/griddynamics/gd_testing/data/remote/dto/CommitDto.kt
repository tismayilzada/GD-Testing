package com.griddynamics.gd_testing.data.remote.dto

import com.google.gson.annotations.SerializedName
import com.griddynamics.gd_testing.domain.model.CommitModel

data class CommitDto(
    @SerializedName("sha")
    val sha: String? = null,
    @SerializedName("node_id")
    val nodeId: String? = null,
    @SerializedName("committer")
    val committer: OwnerDto? = null
)

fun CommitDto.toCommitModel() =
    CommitModel(
        sha = sha ?: "",
        committerName = committer?.username ?: "",
        committerAvatar = committer?.avatar ?: ""
    )
