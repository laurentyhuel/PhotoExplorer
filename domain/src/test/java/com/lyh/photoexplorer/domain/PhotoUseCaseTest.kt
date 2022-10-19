package com.lyh.photoexplorer.domain

import com.lyh.photoexplorer.domain.model.PhotoModel
import com.lyh.photoexplorer.domain.model.PhotoUrlModel
import com.lyh.photoexplorer.domain.repository.IPhotosRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.time.LocalDate

class PhotoUseCaseTest {

    private val photosRepository = mockk<IPhotosRepository>()
    private val photoUseCase = PhotoUseCase(photosRepository)

    @Test
    fun `WHEN get photo THEN return photo`() = runTest {

        val photo = createPhotoModel(5)
        coEvery { photosRepository.getPhoto(photo.id) } returns Result.success(photo)
        val result = photoUseCase.getPhoto(photo.id)
        assertTrue(result.isSuccess)

        assertEquals(photo.id, result.getOrThrow().id)
        coVerify(exactly = 1) { photosRepository.getPhoto(photo.id) }
    }

    private fun createPhotoModel(id: Int) = PhotoModel(
        "$id",
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
