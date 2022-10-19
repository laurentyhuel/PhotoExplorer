package com.lyh.photoexplorer.data.mapper

import com.lyh.photoexplorer.data.remote.dto.UnsplashPhotoStats
import com.lyh.photoexplorer.domain.model.PhotoStatsModel

internal fun UnsplashPhotoStats.toModel() = PhotoStatsModel(
    this.id,
    this.views.total,
    this.downloads.total,
    this.likes.total
)