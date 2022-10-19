package com.lyh.photoexplorer.data.mapper

import com.lyh.photoexplorer.data.remote.dto.UnsplashPhoto
import com.lyh.photoexplorer.domain.model.PhotoModel
import com.lyh.photoexplorer.domain.model.PhotoUrlModel
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime.ofInstant
import java.time.ZoneId

internal fun List<UnsplashPhoto>.toModels() = this.map { it.toModel() }

internal fun UnsplashPhoto.toModel() = PhotoModel(
    this.id,
    PhotoUrlModel(
        thumbUrl = this.urls.thumb ?: this.urls.small,
        url = this.urls.full ?: this.urls.small
    ),
    this.description,
    this.createdAt.toLocalDate(),
    this.color,
    this.likes,
    this.user.username,
    this.user.name,
    this.user.profileImage.small
)

//TODO manage conversion in Kotlinx serialization or retrofit (kotlinx-datetime)
private fun String.toLocalDate(): LocalDate {
    val instant = Instant.parse(this)
    return ofInstant(instant, ZoneId.systemDefault()).toLocalDate();
}