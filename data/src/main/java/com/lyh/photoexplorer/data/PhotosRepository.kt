package com.lyh.photoexplorer.data

import com.lyh.photoexplorer.data.mapper.toModel
import com.lyh.photoexplorer.data.mapper.toModels
import com.lyh.photoexplorer.data.remote.UnsplashApi
import com.lyh.photoexplorer.domain.repository.IPhotosRepository

class PhotosRepository(
    private val unsplashApi: UnsplashApi,
) : IPhotosRepository {

    override suspend fun getPhotos() = unsplashApi.getPhotos().map { it.toModels() }

    override suspend fun getPhoto(photoId: String) =
        unsplashApi.getPhoto(photoId).map { it.toModel() }

    override suspend fun getUserPhotos(userId: String) =
        unsplashApi.getUserPhotos(userId).map { it.toModels() }
}
