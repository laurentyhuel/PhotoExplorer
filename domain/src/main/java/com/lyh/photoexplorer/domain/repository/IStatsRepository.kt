package com.lyh.photoexplorer.domain.repository

import com.lyh.photoexplorer.domain.model.PhotoStatsModel

interface IStatsRepository {

    suspend fun getPhotoStats(photoId: String): Result<PhotoStatsModel>
}