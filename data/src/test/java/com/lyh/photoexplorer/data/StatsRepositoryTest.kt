package com.lyh.photoexplorer.data

import com.lyh.photoexplorer.data.remote.UnsplashApi
import com.lyh.photoexplorer.data.remote.dto.UnsplashPhotoStats
import com.lyh.photoexplorer.data.remote.dto.UnsplashPhotoStatsDetail
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class StatsRepositoryTest {

    private val unsplashApi = mockk<UnsplashApi>()
    private val statsRepository = StatsRepository(unsplashApi)

    @Test
    fun `WHEN get photos succeed THEN return photos`() = runTest {

        val photoId = "5"
        val expected = createPhotoStats(photoId)

        coEvery { unsplashApi.getPhotoStats(photoId) } returns Result.success(expected)
        val result = statsRepository.getPhotoStats(photoId)

        assertTrue(result.isSuccess)

        val data = result.getOrThrow()
        assertEquals(expected.id, data.id)
        assertEquals(expected.likes.total, data.likes)
        assertEquals(expected.downloads.total, data.downloads)
        assertEquals(expected.views.total, data.views)
    }

    @Test
    fun `WHEN get photos failed THEN return failure`() = runTest {
        val photoId = "5"

        coEvery { unsplashApi.getPhotoStats(photoId) } returns Result.failure(
            IllegalArgumentException()
        )
        val result = statsRepository.getPhotoStats(photoId)

        assertTrue(result.isFailure)
    }

    private fun createPhotoStats(id: String) = UnsplashPhotoStats(
        id,
        UnsplashPhotoStatsDetail(40),
        UnsplashPhotoStatsDetail(50),
        UnsplashPhotoStatsDetail(60),
    )
}