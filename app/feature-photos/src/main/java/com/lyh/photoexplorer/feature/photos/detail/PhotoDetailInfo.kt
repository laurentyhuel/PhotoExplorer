package com.lyh.photoexplorer.feature.photos.detail

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
internal fun PhotoDetailInfo(
    @DrawableRes icon: Int,
    info: String,
    color: Color? = null,
    modifier: Modifier = Modifier

) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(20.dp, top = 10.dp, end = 20.dp, bottom = 10.dp)
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = "",
            tint = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
                .size(20.dp)
        )
        Text(
            text = info,
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier
                .padding(start = 20.dp, end = 20.dp)
        )
        color?.let {
            Box(
                modifier = Modifier
                    .size(20.dp)
                    .clip(CircleShape)
                    .background(color = it)
                    .padding(start = 20.dp)
            )
        }
    }
}