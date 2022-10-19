package com.lyh.photoexplorer.feature.photos.mapper

import com.lyh.photoexplorer.domain.model.PhotoModel
import com.lyh.photoexplorer.feature.photos.model.PhotoUi

fun PhotoModel.toUi() = PhotoUi(
    this.id,
    this.photoUrl.toUi(),
    this.description,
    this.createdAt,
    this.color,
    this.likes,
    this.userId,
    this.userName,
    this.userProfilPhotoUrl
)

fun List<PhotoModel>.toUis() = this.map { it.toUi() }
