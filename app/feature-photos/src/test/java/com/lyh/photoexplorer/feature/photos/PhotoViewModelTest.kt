package com.lyh.photoexplorer.feature.photos

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.lyh.photoexplorer.domain.PhotoStatsUseCase
import com.lyh.photoexplorer.domain.PhotoUseCase
import com.lyh.photoexplorer.domain.UserPhotosUseCase
import com.lyh.photoexplorer.domain.model.PhotoModel
import com.lyh.photoexplorer.domain.model.PhotoStatsModel
import com.lyh.photoexplorer.domain.model.PhotoUrlModel
import com.lyh.photoexplorer.feature.core.ResourceError
import com.lyh.photoexplorer.feature.core.ResourceLoading
import com.lyh.photoexplorer.feature.core.ResourceSuccess
import com.lyh.photoexplorer.feature.photos.detail.PhotoViewModel
import com.lyh.photoexplorer.feature.photos.nav.PhotoDetailDestination
import com.lyh.photoexplorer.feature.photos.util.CoroutinesTestExtension
import com.lyh.photoexplorer.feature.photos.util.InstantExecutorExtension
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.time.LocalDate

@ExtendWith(InstantExecutorExtension::class, CoroutinesTestExtension::class)
class PhotoViewModelTest {

    private val photoId = "5"
    private val photos = List(10) { index -> createPhotoModel(index.toString()) }
    private val savedStateHandle = mockk<SavedStateHandle> {
        every { get<String>(PhotoDetailDestination.photoIdArg) } returns photoId
    }

    @Test
    fun `WHEN get photo succeed THEN get data`() = runTest {
        val photo = createPhotoModel(photoId)
        val photoUseCase = mockk<PhotoUseCase> {
            coEvery { getPhoto(photoId) } returns Result.success(photo)
        }
        val photoStats = createPhotoStatsModel(photoId)
        val photoStatsUseCase = mockk<PhotoStatsUseCase> {
            coEvery { getPhotoStats(photoId) } returns Result.success(photoStats)
        }
        val userPhotosUseCase = mockk<UserPhotosUseCase> {
            coEvery { getUsersPhotosWithoutCurrent(photo.userId, photoId) } returns Result.success(
                photos
            )
        }
        val photoViewModel =
            PhotoViewModel(savedStateHandle, photoUseCase, photoStatsUseCase, userPhotosUseCase)

        photoViewModel.details.test {
            val result = awaitItem()
            assertTrue(result is ResourceSuccess)
            val photoResult = result as ResourceSuccess
            assertEquals(photo.id, photoResult.data.photoModel.id)
            assertEquals(photo.description, photoResult.data.photoModel.description)
            assertEquals(photoStats.downloads, photoResult.data.downloads)
            assertEquals(photoStats.views, photoResult.data.views)
            assertEquals(photoStats.likes, photoResult.data.likes)
            assertEquals(photos.size, photoResult.data.userPhotos.size)
        }

        coVerify(exactly = 1) { photoUseCase.getPhoto(photoId) }
        coVerify(exactly = 1) { photoStatsUseCase.getPhotoStats(photoId) }
        coVerify(exactly = 1) {
            userPhotosUseCase.getUsersPhotosWithoutCurrent(
                photo.userId,
                photoId
            )
        }
    }

    @Test
    fun `WHEN init THEN get photo return loading`() = runTest {
        val photoUseCase = mockk<PhotoUseCase>(relaxed = true)
        val photoStatsUseCase = mockk<PhotoStatsUseCase>(relaxed = true)
        val userPhotosUseCase = mockk<UserPhotosUseCase>(relaxed = true)
        val photoViewModel =
            PhotoViewModel(savedStateHandle, photoUseCase, photoStatsUseCase, userPhotosUseCase)

        photoViewModel.details.test {
            val result = awaitItem()
            assertTrue(result is ResourceLoading)
        }
    }

    @Test
    fun `WHEN get photo and user-photos failed THEN failure`() = runTest {
        val photo = createPhotoModel(photoId)
        val photoUseCase = mockk<PhotoUseCase> {
            coEvery { getPhoto(photoId) } returns Result.success(photo)
        }
        val photoStats = createPhotoStatsModel(photoId)
        val photoStatsUseCase = mockk<PhotoStatsUseCase> {
            coEvery { getPhotoStats(photoId) } returns Result.success(photoStats)
        }
        val userPhotosUseCase = mockk<UserPhotosUseCase> {
            coEvery { getUsersPhotosWithoutCurrent(photo.userId, photoId) } returns Result.failure(
                IllegalArgumentException()
            )
        }
        val photoViewModel =
            PhotoViewModel(savedStateHandle, photoUseCase, photoStatsUseCase, userPhotosUseCase)

        photoViewModel.details.test {
            val result = awaitItem()
            assertTrue(result is ResourceError)
        }
        coVerify(exactly = 1) { photoUseCase.getPhoto(photoId) }
        coVerify(exactly = 1) { photoStatsUseCase.getPhotoStats(photoId) }
        coVerify(exactly = 1) {
            userPhotosUseCase.getUsersPhotosWithoutCurrent(
                photo.userId,
                photoId
            )
        }
    }

    @Test
    fun `WHEN get photo and photo-stats failed THEN failure`() = runTest {
        val photo = createPhotoModel(photoId)
        val photoUseCase = mockk<PhotoUseCase> {
            coEvery { getPhoto(photoId) } returns Result.success(photo)
        }
        val photoStatsUseCase = mockk<PhotoStatsUseCase> {
            coEvery { getPhotoStats(photoId) } returns Result.failure(IllegalArgumentException())
        }
        val userPhotosUseCase = mockk<UserPhotosUseCase> {
            coEvery { getUsersPhotosWithoutCurrent(photo.userId, photoId) } returns Result.success(
                photos
            )
        }
        val photoViewModel =
            PhotoViewModel(savedStateHandle, photoUseCase, photoStatsUseCase, userPhotosUseCase)

        photoViewModel.details.test {
            val result = awaitItem()
            assertTrue(result is ResourceError)
        }

        coVerify(exactly = 1) { photoUseCase.getPhoto(photoId) }
        coVerify(exactly = 1) { photoStatsUseCase.getPhotoStats(photoId) }
        coVerify(exactly = 0) {
            userPhotosUseCase.getUsersPhotosWithoutCurrent(
                photo.userId,
                photoId
            )
        }
    }

    @Test
    fun `WHEN get photo and photo failed THEN failure`() = runTest {
        val photo = createPhotoModel(photoId)
        val photoUseCase = mockk<PhotoUseCase> {
            coEvery { getPhoto(photoId) } returns Result.failure(IllegalArgumentException())
        }
        val photoStats = createPhotoStatsModel(photoId)
        val photoStatsUseCase = mockk<PhotoStatsUseCase> {
            coEvery { getPhotoStats(photoId) } returns Result.success(photoStats)
        }
        val userPhotosUseCase = mockk<UserPhotosUseCase> {
            coEvery { getUsersPhotosWithoutCurrent(photo.userId, photoId) } returns Result.success(
                photos
            )
        }
        val photoViewModel =
            PhotoViewModel(savedStateHandle, photoUseCase, photoStatsUseCase, userPhotosUseCase)

        photoViewModel.details.test {
            val result = awaitItem()
            assertTrue(result is ResourceError)
        }

        coVerify(exactly = 1) { photoUseCase.getPhoto(photoId) }
        coVerify(exactly = 0) { photoStatsUseCase.getPhotoStats(photoId) }
        coVerify(exactly = 0) {
            userPhotosUseCase.getUsersPhotosWithoutCurrent(
                photo.userId,
                photoId
            )
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

    private fun createPhotoStatsModel(id: String) = PhotoStatsModel(
        id,
        40,
        50,
        60,
    )
}
