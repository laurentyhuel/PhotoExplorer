package com.lyh.photoexplorer.domain

import com.lyh.photoexplorer.domain.repository.IStatsRepository

class PhotoStatsUseCase(private val statsRepository: IStatsRepository) {

    suspend fun getPhotoStats(photoId: String) = statsRepository.getPhotoStats(photoId)
}