package com.lyh.photoexplorer.domain

import com.lyh.photoexplorer.domain.core.runCatchingOnResult
import com.lyh.photoexplorer.domain.model.PhotoModel
import com.lyh.photoexplorer.domain.repository.IPhotosRepository

class UserPhotosUseCase(private val photoRepository: IPhotosRepository) {

    suspend fun getUsersPhotosWithoutCurrent(
        userId: String,
        currentPhotoId: String
    ): Result<List<PhotoModel>> = runCatchingOnResult {
        val result = photoRepository.getUserPhotos(userId)
        if (result.isSuccess) {

            return Result.success(result.getOrThrow().filterNot { it.id == currentPhotoId })
        }
        return result
    }
}
