package com.lyh.photoexplorer.feature.photos.mapper

import com.lyh.photoexplorer.domain.model.PhotoUrlModel
import com.lyh.photoexplorer.feature.photos.model.PhotoUrlUi

fun PhotoUrlModel.toUi() = PhotoUrlUi(
    this.thumbUrl,
    this.url
)

fun List<PhotoUrlModel>.toUis() = this.map { it.toUi() }