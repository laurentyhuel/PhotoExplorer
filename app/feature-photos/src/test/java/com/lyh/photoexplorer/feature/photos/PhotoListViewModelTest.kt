package com.lyh.photoexplorer.feature.photos

import app.cash.turbine.test
import com.lyh.photoexplorer.domain.PhotosUseCase
import com.lyh.photoexplorer.domain.model.PhotoModel
import com.lyh.photoexplorer.domain.model.PhotoUrlModel
import com.lyh.photoexplorer.feature.core.ResourceError
import com.lyh.photoexplorer.feature.core.ResourceLoading
import com.lyh.photoexplorer.feature.core.ResourceSuccess
import com.lyh.photoexplorer.feature.photos.list.PhotoListViewModel
import com.lyh.photoexplorer.feature.photos.util.CoroutinesTestExtension
import com.lyh.photoexplorer.feature.photos.util.InstantExecutorExtension
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.time.LocalDate

@ExtendWith(
    InstantExecutorExtension::class,
    CoroutinesTestExtension::class
)
class PhotoListViewModelTest {

    private val photos = List(10) { index -> createPhotoModel(index.toString()) }

    private val photosUseCase = mockk<PhotosUseCase>()

    private lateinit var photoListViewModel: PhotoListViewModel

    @Test
    fun `WHEN get photos THEN return state with photos`() = runTest {
        coEvery { photosUseCase.getPhotos() } returns Result.success(photos)
        photoListViewModel = PhotoListViewModel(photosUseCase)

        photoListViewModel.photos.test {
            val dataSuccess = awaitItem()
            assertTrue(dataSuccess is ResourceSuccess)
            val resultSuccess = dataSuccess as ResourceSuccess
            assertEquals(photos.size, resultSuccess.data.size)
        }
    }

    @Test
    fun `WHEN get photos loading THEN return loading`() = runTest {
        val photosUseCaseRelaxed = mockk<PhotosUseCase>(relaxed = true)
        photoListViewModel = PhotoListViewModel(photosUseCaseRelaxed)

        photoListViewModel.photos.test {
            val dataSuccess = awaitItem()
            assertTrue(dataSuccess is ResourceLoading)
        }
    }

    @Test
    fun `WHEN get photos failed THEN return failure`() = runTest {
        coEvery { photosUseCase.getPhotos() } returns Result.failure(IllegalArgumentException())
        photoListViewModel = PhotoListViewModel(photosUseCase)

        photoListViewModel.photos.test {
            val dataSuccess = awaitItem()
            assertTrue(dataSuccess is ResourceError)
        }
    }

    private fun createPhotoModel(id: String) = PhotoModel(
        id,
        PhotoUrlModel(
            "https://images.unsplash.com/photo-1661956602944-249bcd04b63f?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=MnwzNzM0NzN8MXwxfGFsbHw2fHx8fHx8Mnx8MTY2NjE2NDEwNQ&ixlib=rb-4.0.3&q=80&w=200",
            "https://images.unsplash.com/photo-1661956602944-249bcd04b63f?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=MnwzNzM0NzN8MXwxfGFsbHw2fHx8fHx8Mnx8MTY2NjE2NDEwNQ&ixlib=rb-4.0.3&q=80&w=1080",

            ),
        "description $id",
        LocalDate.now(),
        "#c0c0c0",
        50,
        "userId$id",
        "userName $id",
        "https://images.unsplash.com/profile-1665554136768-6615667f6670image?ixlib=rb-4.0.3&crop=faces&fit=crop&w=32&h=32"
    )
}
