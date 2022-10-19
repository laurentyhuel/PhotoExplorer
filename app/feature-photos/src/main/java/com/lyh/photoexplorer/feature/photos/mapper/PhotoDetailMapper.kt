package com.lyh.photoexplorer.feature.photos.mapper

import com.lyh.photoexplorer.domain.model.PhotoModel
import com.lyh.photoexplorer.domain.model.PhotoStatsModel
import com.lyh.photoexplorer.feature.photos.model.PhotoDetailUi

fun mapDataToPhotoDetailUi(
    photoModel: PhotoModel,
    photoStatsModel: PhotoStatsModel,
    userPhotos: List<PhotoModel>,
) = PhotoDetailUi(
    photoModel,
    photoStatsModel.views,
    photoStatsModel.downloads,
    photoStatsModel.likes,
    userPhotos.map { it.photoUrl }.toUis()
)