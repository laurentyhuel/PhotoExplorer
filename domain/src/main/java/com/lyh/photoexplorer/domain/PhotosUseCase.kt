package com.lyh.photoexplorer.domain

import com.lyh.photoexplorer.domain.repository.IPhotosRepository

class PhotosUseCase(private val photoRepository: IPhotosRepository) {

    suspend fun getPhotos() = photoRepository.getPhotos()
}
