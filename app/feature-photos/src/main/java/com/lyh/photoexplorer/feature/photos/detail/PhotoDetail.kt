package com.lyh.photoexplorer.feature.photos.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import coil.compose.AsyncImage
import com.lyh.photoexplorer.feature.photos.R
import com.lyh.photoexplorer.feature.photos.model.PhotoDetailUi
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
internal fun PhotoDetail(
    modifier: Modifier = Modifier,
    photoDetail: PhotoDetailUi,
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        bottomBar = { PhotoDetailFooter(photoDetail) },
    ) { innerPadding ->
        val size = LocalConfiguration.current.screenWidthDp.dp

        Column(
            Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        ) {
            AsyncImage(
                model = photoDetail.photoModel.photoUrl.url,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(size)
                    .padding(
                        top = innerPadding.calculateTopPadding(),
                    )
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.tertiaryContainer)
                    .padding(start = 20.dp, top = 5.dp, end = 20.dp, bottom = 5.dp)
            ) {
                photoDetail.photoModel.userProfilPhotoUrl?.let {
                    AsyncImage(
                        model = it,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(30.dp)
                            .clip(CircleShape)
                    )
                }
                Text(
                    text = stringResource(id = R.string.photo_by, photoDetail.photoModel.userName),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier
                        .padding(start = 20.dp)
                )
            }
            photoDetail.photoModel.description?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier
                        .padding(start = 20.dp, top = 5.dp, end = 20.dp, bottom = 5.dp)
                )
            }
            PhotoDetailInfo(
                icon = R.drawable.calendar,
                info = photoDetail.photoModel.createdAt.format(DateTimeFormatter.ISO_DATE)
            )
            PhotoDetailInfo(
                icon = R.drawable.heart,
                info = pluralStringResource(R.plurals.likes, photoDetail.likes, photoDetail.likes)
            )
            PhotoDetailInfo(
                icon = R.drawable.download,
                info = pluralStringResource(
                    R.plurals.downloads,
                    photoDetail.downloads,
                    photoDetail.downloads
                )
            )
            PhotoDetailInfo(
                icon = R.drawable.view,
                info = pluralStringResource(R.plurals.views, photoDetail.views, photoDetail.views)
            )
            photoDetail.photoModel.color?.let {
                PhotoDetailInfo(
                    icon = R.drawable.palette,
                    info = it,
                    color = Color(it.toColorInt()),
                    modifier = Modifier.padding(bottom = innerPadding.calculateBottomPadding())
                )
            }
        }
    }
}