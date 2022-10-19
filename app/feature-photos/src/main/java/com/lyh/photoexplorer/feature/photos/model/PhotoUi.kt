package com.lyh.photoexplorer.feature.photos.model

import java.time.LocalDate

data class PhotoUi(
    val id: String,
    val photoUrl: PhotoUrlUi,
    val description: String?,
    val createdAt: LocalDate,
    val color: String? = "#000000",
    val likes: Int,
    val userId: String,
    val userName: String,
    val userProfilePhotoUrl: String?,
)