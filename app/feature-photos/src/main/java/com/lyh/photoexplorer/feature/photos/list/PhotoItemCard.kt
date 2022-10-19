package com.lyh.photoexplorer.feature.photos.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.lyh.photoexplorer.feature.photos.model.PhotoUi

@Composable
internal fun PhotoItemCard(
    modifier: Modifier = Modifier,
    photoUi: PhotoUi,
    onClick: (photoId: String) -> Unit,
) {
    val size = LocalConfiguration.current.screenWidthDp / NB_ITEMS_BY_ROW
    Box(
        modifier = modifier
            .size(size.dp)
            .clickable { onClick(photoUi.id) }
    ) {
        AsyncImage(
            model = photoUi.photoUrl.thumbUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
        )
        if (photoUi.likes > 0) {
            Text(
                text = "${photoUi.likes} \uD83E\uDD0D",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.align(Alignment.TopEnd)
            )
        }
        Text(
            text = "@ ${photoUi.userName}",
            style = MaterialTheme.typography.bodySmall,
            maxLines = 1,
            modifier = Modifier.align(Alignment.BottomStart)
        )
    }
}