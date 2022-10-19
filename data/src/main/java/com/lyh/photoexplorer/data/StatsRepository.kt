package com.lyh.photoexplorer.data

import com.lyh.photoexplorer.data.mapper.toModel
import com.lyh.photoexplorer.data.remote.UnsplashApi
import com.lyh.photoexplorer.domain.repository.IStatsRepository

class StatsRepository(
    private val unsplashApi: UnsplashApi,
) : IStatsRepository {

    override suspend fun getPhotoStats(photoId: String) =
        unsplashApi.getPhotoStats(photoId).map { it.toModel() }
}