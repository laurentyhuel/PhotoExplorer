package com.lyh.photoexplorer.feature.photos.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lyh.photoexplorer.domain.PhotosUseCase
import com.lyh.photoexplorer.feature.core.Resource
import com.lyh.photoexplorer.feature.core.ResourceError
import com.lyh.photoexplorer.feature.core.ResourceLoading
import com.lyh.photoexplorer.feature.core.ResourceSuccess
import com.lyh.photoexplorer.feature.photos.mapper.toUis
import com.lyh.photoexplorer.feature.photos.model.PhotoUi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber

class PhotoListViewModel(
    private val photosUseCase: PhotosUseCase
) : ViewModel() {

    private val photosTrigger: MutableSharedFlow<Unit> = MutableSharedFlow(replay = 1)

    init {
        triggerPhotos()
    }

    val photos: StateFlow<Resource<List<PhotoUi>>> =
        photosTrigger.flatMapLatest {
            getPhotosFlow()
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ResourceLoading()
        )

    private fun getPhotosFlow(): Flow<Resource<List<PhotoUi>>> =
        flow {
            photosUseCase.getPhotos()
                .onSuccess {
                    emit(ResourceSuccess(it.toUis()))
                }
                .onFailure {
                    Timber.e(it, "Failed to getPhotos ${it.message}")
                    emit(ResourceError(it.localizedMessage))
                }
        }

    fun triggerPhotos() = viewModelScope.launch {
        photosTrigger.emit(Unit)
    }
}