package com.lyh.photoexplorer.domain

import com.lyh.photoexplorer.domain.core.runCatchingOnResult
import com.lyh.photoexplorer.domain.model.PhotoStatsModel
import com.lyh.photoexplorer.domain.repository.IStatsRepository

class PhotoStatsUseCase(private val statsRepository: IStatsRepository) {

    suspend fun getPhotoStats(photoId: String): Result<PhotoStatsModel> = runCatchingOnResult {
        statsRepository.getPhotoStats(photoId)
    }
}