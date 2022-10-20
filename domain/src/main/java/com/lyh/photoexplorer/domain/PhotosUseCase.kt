package com.lyh.photoexplorer.domain

import com.lyh.photoexplorer.domain.core.runCatchingOnResult
import com.lyh.photoexplorer.domain.model.PhotoModel
import com.lyh.photoexplorer.domain.repository.IPhotosRepository

class PhotosUseCase(private val photoRepository: IPhotosRepository) {

    suspend fun getPhotos(): Result<List<PhotoModel>> = runCatchingOnResult {
        photoRepository.getPhotos()
    }
}
