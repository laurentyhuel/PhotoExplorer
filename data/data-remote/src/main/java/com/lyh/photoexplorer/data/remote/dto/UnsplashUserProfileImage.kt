package com.lyh.photoexplorer.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class UnsplashUserProfileImage(
    val small: String?,
    val medium: String?,
    val large: String?,
)