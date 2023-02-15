package com.griddynamics.gd_testing.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RepositoryModel(
    val name: String,
    val description: String,
    val ownerName: String
): Parcelable
