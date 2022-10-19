package com.lyh.photoexplorer.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UnsplashPhoto(
    val id: String,
    @SerialName("created_at")
    val createdAt: String,
    val width: Int,
    val height: Int,
    val color: String? = "#000000",
    val likes: Int,
    val description: String?,
    val urls: UnsplashUrls,
    val user: UnsplashUser
)