package com.lyh.photoexplorer.data.remote.dto

@kotlinx.serialization.Serializable
data class UnsplashPhotoStats(
    val id: String,
    val downloads: UnsplashPhotoStatsDetail,
    val views: UnsplashPhotoStatsDetail,
    val likes: UnsplashPhotoStatsDetail,
)