package com.lyh.photoexplorer.feature.photos.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lyh.photoexplorer.domain.PhotoStatsUseCase
import com.lyh.photoexplorer.domain.PhotoUseCase
import com.lyh.photoexplorer.domain.UserPhotosUseCase
import com.lyh.photoexplorer.feature.core.Resource
import com.lyh.photoexplorer.feature.core.ResourceError
import com.lyh.photoexplorer.feature.core.ResourceLoading
import com.lyh.photoexplorer.feature.core.ResourceSuccess
import com.lyh.photoexplorer.feature.photos.mapper.mapDataToPhotoDetailUi
import com.lyh.photoexplorer.feature.photos.model.PhotoDetailUi
import com.lyh.photoexplorer.feature.photos.nav.PhotoDetailDestination
import kotlinx.coroutines.flow.*
import timber.log.Timber

class PhotoViewModel(
    savedStateHandle: SavedStateHandle,
    private val photoUseCase: PhotoUseCase,
    private val photoStatsUseCase: PhotoStatsUseCase,
    private val userPhotosUseCase: UserPhotosUseCase
) : ViewModel() {

    private val photoId: String =
        checkNotNull(savedStateHandle[PhotoDetailDestination.photoIdArg])

    val details: StateFlow<Resource<PhotoDetailUi>> =
        getPhotoDetailFlow(photoId)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = ResourceLoading()
            )

    private fun getPhotoDetailFlow(id: String): Flow<Resource<PhotoDetailUi>> = flow {
        //TODO to improve : try to use Flow#combine insteadof nesting
        photoUseCase.getPhoto(id)
            .onSuccess { photoModel ->
                photoStatsUseCase.getPhotoStats(id)
                    .onSuccess { photoStatsModel ->
                        userPhotosUseCase.getUsersPhotosWithoutCurrent(photoModel.userId, id)
                            .onSuccess { userPhotos ->
                                val photoDetailUi =
                                    mapDataToPhotoDetailUi(photoModel, photoStatsModel, userPhotos)
                                emit(ResourceSuccess(photoDetailUi))
                            }
                            .onFailure {
                                Timber.e(it, "Failed to getUserPhotos ${it.message}")
                                emit(ResourceError(it.localizedMessage))
                            }
                    }
                    .onFailure {
                        Timber.e(it, "Failed to getPhoto stat ${it.message}")
                        emit(ResourceError(it.localizedMessage))
                    }
            }
            .onFailure {
                Timber.e(it, "Failed to getPhoto ${it.message}")
                emit(ResourceError(it.localizedMessage))
            }
    }
}