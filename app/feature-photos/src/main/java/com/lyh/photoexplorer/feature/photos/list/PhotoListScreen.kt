package com.lyh.photoexplorer.feature.photos.list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.lyh.photoexplorer.feature.core.Resource
import com.lyh.photoexplorer.feature.core.ResourceError
import com.lyh.photoexplorer.feature.core.ResourceLoading
import com.lyh.photoexplorer.feature.core.ResourceSuccess
import com.lyh.photoexplorer.feature.core.ui.ErrorComponent
import com.lyh.photoexplorer.feature.core.ui.LoadingComponent
import com.lyh.photoexplorer.feature.photos.model.PhotoUi
import com.lyh.photoexplorer.feature.photos.model.PhotoUrlUi
import org.koin.androidx.compose.getViewModel
import java.time.LocalDate
import com.lyh.photoexplorer.feature.core.R as RCore

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun PhotoListRoute(
    modifier: Modifier = Modifier,
    onNavigateToPhoto: (photoId: String) -> Unit,
    viewModel: PhotoListViewModel = getViewModel()
) {
    val state by viewModel.photos.collectAsStateWithLifecycle()

    PhotoListScreen(
        modifier = modifier,
        state,
        retry = viewModel::triggerPhotos,
        onNavigateToPhoto = onNavigateToPhoto
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhotoListScreen(
    modifier: Modifier = Modifier,
    state: Resource<List<PhotoUi>>,
    retry: () -> Unit,
    onNavigateToPhoto: (photoId: String) -> Unit
) {
    Scaffold(
        modifier = modifier,
//        topBar = {
//            AppTopBar(
//                title = stringResource(id = RCore.string.app_name),
//                onBackClick = null
//            )
//        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (state) {
                is ResourceError -> ErrorComponent(
                    errorText = stringResource(id = RCore.string.fetch_exception),
                    retry = retry,
                    modifier = Modifier.padding(10.dp)
                )
                is ResourceLoading -> LoadingComponent(
                    loadingText = stringResource(id = RCore.string.loading_data),
                    modifier = Modifier.padding(10.dp)
                )
                is ResourceSuccess -> PhotoList(
                    photos = state.data,
                    onNavigateToPhoto = onNavigateToPhoto,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
        }
    }
}

@Composable
private fun PhotoList(
    modifier: Modifier = Modifier,
    photos: List<PhotoUi>,
    onNavigateToPhoto: (photoId: String) -> Unit
) = LazyVerticalGrid(
    columns = GridCells.Fixed(NB_ITEMS_BY_ROW),
    modifier = modifier
        .fillMaxWidth()
) {
    photos.forEach {
        item(key = it.id) {
            PhotoItemCard(photoUi = it, onClick = onNavigateToPhoto)
        }
    }
}

@Preview
@Composable
fun PhotoItemCardPreview() {
    PhotoItemCard(
        photoUi = PhotoUi(
            id = "4654654",
            photoUrl = PhotoUrlUi(
                "https://images.unsplash.com/photo-1664574653006-4d7eb5f1a418?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=MnwzNzM0NzN8MXwxfGFsbHwxfHx8fHx8Mnx8MTY2NjE2NDEwNQ&ixlib=rb-1.2.1&q=80&w=200",
                "https://images.unsplash.com/photo-1664574653006-4d7eb5f1a418?ixid=MnwzNzM0NzN8MXwxfGFsbHwxfHx8fHx8Mnx8MTY2NjE2NDEwNQ&ixlib=rb-1.2.1",
            ),
            description = "Abstract shape covered with textured cloth. work \uD83D\uDC49 Shubhamdhage000@gmail.com",
            createdAt = LocalDate.now(),
            likes = 456,
            userId = "4458778",
            userName = "Shubham Dhage",
            userProfilePhotoUrl = "https://images.unsplash.com/profile-1665554136768-6615667f6670image?ixlib=rb-4.0.3&crop=faces&fit=crop&w=32&h=32"
        ),
        onClick = {}
    )
}

// TODO this value could be dynamic depending on screen orientation and screen size
internal const val NB_ITEMS_BY_ROW = 3
