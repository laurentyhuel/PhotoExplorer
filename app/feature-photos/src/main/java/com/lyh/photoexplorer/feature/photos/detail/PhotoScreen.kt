package com.lyh.photoexplorer.feature.photos.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.lyh.photoexplorer.feature.core.Resource
import com.lyh.photoexplorer.feature.core.ResourceError
import com.lyh.photoexplorer.feature.core.ResourceLoading
import com.lyh.photoexplorer.feature.core.ResourceSuccess
import com.lyh.photoexplorer.feature.core.ui.ErrorComponent
import com.lyh.photoexplorer.feature.core.ui.LoadingComponent
import com.lyh.photoexplorer.feature.photos.model.PhotoDetailUi
import org.koin.androidx.compose.getViewModel
import com.lyh.photoexplorer.feature.core.R as RCore

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun PhotoRoute(
    modifier: Modifier = Modifier,
    viewModel: PhotoViewModel = getViewModel(),
    onBackClick: () -> Unit,
) {
    val detailsResource by viewModel.details.collectAsStateWithLifecycle()

    PhotoScreen(
        modifier = modifier,
        detailsResource = detailsResource,
        onBackClick = onBackClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhotoScreen(
    modifier: Modifier = Modifier,
    detailsResource: Resource<PhotoDetailUi>,
    onBackClick: () -> Unit,
) {
    Scaffold(
        modifier = modifier,
//        topBar = {
//            AppTopBar(
//                title = stringResource(id = RCore.string.app_name),
//                onBackClick = onBackClick
//            )
//        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            when (detailsResource) {
                is ResourceError -> ErrorComponent(
                    errorText = stringResource(id = RCore.string.fetch_exception),
                    retry = null,
                )
                is ResourceLoading -> LoadingComponent(loadingText = stringResource(id = RCore.string.loading_data))
                is ResourceSuccess -> PhotoDetail(
                    photoDetail = detailsResource.data,
                )
            }
        }
    }
}

