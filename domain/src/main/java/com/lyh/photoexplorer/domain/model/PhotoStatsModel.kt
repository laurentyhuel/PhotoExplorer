package com.lyh.photoexplorer.domain.model

data class PhotoStatsModel(
    val id: String,
    val views: Int,
    val downloads: Int,
    val likes: Int,
)
