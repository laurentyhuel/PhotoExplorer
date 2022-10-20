package com.lyh.photoexplorer.domain

import com.lyh.photoexplorer.domain.core.runCatchingOnResult
import com.lyh.photoexplorer.domain.model.PhotoModel
import com.lyh.photoexplorer.domain.repository.IPhotosRepository

class PhotoUseCase(private val photoRepository: IPhotosRepository) {

    suspend fun getPhoto(photoId: String): Result<PhotoModel> = runCatchingOnResult {
        photoRepository.getPhoto(photoId)
    }
}
