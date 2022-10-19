package com.lyh.photoexplorer.domain

import com.lyh.photoexplorer.domain.repository.IPhotosRepository

class PhotoUseCase(private val photoRepository: IPhotosRepository) {

    suspend fun getPhoto(photoId: String) = photoRepository.getPhoto(photoId)
}
