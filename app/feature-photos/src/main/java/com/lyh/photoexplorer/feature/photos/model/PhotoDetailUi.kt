package com.lyh.photoexplorer.feature.photos.model

import com.lyh.photoexplorer.domain.model.PhotoModel

data class PhotoDetailUi(
    val photoModel: PhotoModel,
    val views: Int,
    val downloads: Int,
    val likes: Int,
    val userPhotos: List<PhotoUrlUi>
)