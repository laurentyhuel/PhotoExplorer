package com.lyh.photoexplorer.data.remote.dto

/**
 * ignore historical data for this project
 */
@kotlinx.serialization.Serializable
data class UnsplashPhotoStatsDetail(
    val total: Int,
)