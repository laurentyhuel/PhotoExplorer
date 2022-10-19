package com.lyh.photoexplorer.domain

import com.lyh.photoexplorer.domain.model.PhotoStatsModel
import com.lyh.photoexplorer.domain.repository.IStatsRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class PhotoStatsUseCaseTest {

    private val statsRepository = mockk<IStatsRepository>()
    private val photoStatsUseCase = PhotoStatsUseCase(statsRepository)

    @Test
    fun `WHEN get user THEN call userRepository#getUser()`() = runTest {
        val photoId = "5"

        coEvery { statsRepository.getPhotoStats(photoId) } returns Result.success(
            createPhotoStatsModel(photoId)
        )
        val result = photoStatsUseCase.getPhotoStats(photoId)

        assertTrue(result.isSuccess)

        assertEquals(photoId, result.getOrThrow().id)
        coVerify(exactly = 1) { photoStatsUseCase.getPhotoStats(photoId) }
    }

    private fun createPhotoStatsModel(id: String) = PhotoStatsModel(
        id,
        40,
        50,
        60,
    )
}