package com.lyh.photoexplorer.feature.photos.detail

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.lyh.photoexplorer.feature.photos.model.PhotoDetailUi

@Composable
internal fun PhotoDetailFooter(
    photoDetail: PhotoDetailUi
) {
    if (photoDetail.userPhotos.isNotEmpty()) {
        LazyRow(modifier = Modifier.fillMaxWidth()) {
            items(items = photoDetail.userPhotos, key = { it.url }) {
                AsyncImage(
                    model = it.thumbUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(80.dp)
                )
            }
        }
    }
}