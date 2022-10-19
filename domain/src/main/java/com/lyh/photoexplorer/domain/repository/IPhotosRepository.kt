package com.lyh.photoexplorer.domain.repository

import com.lyh.photoexplorer.domain.model.PhotoModel

interface IPhotosRepository {

    suspend fun getPhotos(): Result<List<PhotoModel>>

    suspend fun getPhoto(photoId: String): Result<PhotoModel>

    suspend fun getUserPhotos(userId: String): Result<List<PhotoModel>>
}
