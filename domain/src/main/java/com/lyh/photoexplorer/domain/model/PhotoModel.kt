package com.lyh.photoexplorer.domain.model

import java.time.LocalDate

data class PhotoModel(
    val id: String,
    val photoUrl: PhotoUrlModel,
    val description: String?,
    val createdAt: LocalDate,
    val color: String? = "#000000",
    val likes: Int,
    val userId: String,
    val userName: String,
    val userProfilPhotoUrl: String?,
)