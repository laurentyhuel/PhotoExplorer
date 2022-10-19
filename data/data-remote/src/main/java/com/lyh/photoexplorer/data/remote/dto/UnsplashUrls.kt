package com.lyh.photoexplorer.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class UnsplashUrls(
    val thumb: String?,
    val small: String,
    val regular: String?,
    val full: String?,
    val raw: String?
)