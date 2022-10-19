package com.lyh.photoexplorer.data

import com.lyh.photoexplorer.data.remote.UnsplashApi
import com.lyh.photoexplorer.data.remote.dto.UnsplashPhoto
import com.lyh.photoexplorer.data.remote.dto.UnsplashUrls
import com.lyh.photoexplorer.data.remote.dto.UnsplashUser
import com.lyh.photoexplorer.data.remote.dto.UnsplashUserProfileImage
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class PhotosRepositoryTest {
    private val photosFromRemote = List(10) { index -> createPhoto(index) }

    private val unsplashApi = mockk<UnsplashApi>()
    private val photoRepository = PhotosRepository(unsplashApi)

    @Test
    fun `WHEN get photos succeed THEN return photos`() = runTest {

        coEvery { unsplashApi.getPhotos() } returns Result.success(photosFromRemote)
        val result = photoRepository.getPhotos()

        assertTrue(result.isSuccess)

        val list = result.getOrThrow()
        assertEquals(photosFromRemote.size, list.size)
    }

    @Test
    fun `WHEN get photos failed THEN return failure`() = runTest {

        coEvery { unsplashApi.getPhotos() } returns Result.failure(IllegalArgumentException())
        val result = photoRepository.getPhotos()

        assertTrue(result.isFailure)
    }

    @Test
    fun `WHEN get photo by id succeed THEN return expected photo`() = runTest {
        val photo = createPhoto(5)

        coEvery { unsplashApi.getPhoto(photo.id) } returns Result.success(photo)

        val result = photoRepository.getPhoto(photo.id)

        assertTrue(result.isSuccess)
        val data = result.getOrThrow()

        assertEquals(photo.id, data.id)
        assertEquals(photo.user.name, data.userName)
    }

    @Test
    fun `WHEN get photo by id failed THEN return failure`() = runTest {
        val photoId = "5"
        coEvery { unsplashApi.getPhoto(photoId) } returns Result.failure(IllegalArgumentException())

        val result = photoRepository.getPhoto(photoId)

        assertTrue(result.isFailure)
    }

    @Test
    fun `WHEN get user photos succeed THEN return expected user photos`() = runTest {
        val userId = "5"

        coEvery { unsplashApi.getUserPhotos(userId) } returns Result.success(photosFromRemote)

        val result = photoRepository.getUserPhotos(userId)

        assertTrue(result.isSuccess)
        val list = result.getOrThrow()

        assertEquals(photosFromRemote.size, list.size)
    }

    @Test
    fun `WHEN get user photos failed THEN return failure`() = runTest {
        val photoId = "5"
        coEvery { unsplashApi.getUserPhotos(photoId) } returns Result.failure(
            IllegalArgumentException()
        )

        val result = photoRepository.getUserPhotos(photoId)

        assertTrue(result.isFailure)
    }

    private fun createPhoto(id: Int) = UnsplashPhoto(
        "$id",
        "2022-08-31T14:36:55Z",
        2000,
        2000,
        "#c0c0c0",
        12,
        "description $id",
        UnsplashUrls(
            raw = "https://images.unsplash.com/photo-1661956602944-249bcd04b63f?ixid=MnwzNzM0NzN8MXwxfGFsbHw2fHx8fHx8Mnx8MTY2NjE2NDEwNQ&ixlib=rb-4.0.3",
            full = "https://images.unsplash.com/photo-1661956602944-249bcd04b63f?crop=entropy&cs=tinysrgb&fm=jpg&ixid=MnwzNzM0NzN8MXwxfGFsbHw2fHx8fHx8Mnx8MTY2NjE2NDEwNQ&ixlib=rb-4.0.3&q=80",
            regular = "https://images.unsplash.com/photo-1661956602944-249bcd04b63f?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=MnwzNzM0NzN8MXwxfGFsbHw2fHx8fHx8Mnx8MTY2NjE2NDEwNQ&ixlib=rb-4.0.3&q=80&w=1080",
            small = "https://images.unsplash.com/photo-1661956602944-249bcd04b63f?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=MnwzNzM0NzN8MXwxfGFsbHw2fHx8fHx8Mnx8MTY2NjE2NDEwNQ&ixlib=rb-4.0.3&q=80&w=400",
            thumb = "https://images.unsplash.com/photo-1661956602944-249bcd04b63f?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=MnwzNzM0NzN8MXwxfGFsbHw2fHx8fHx8Mnx8MTY2NjE2NDEwNQ&ixlib=rb-4.0.3&q=80&w=200",
        ),
        UnsplashUser(
            id = "user $id",
            username = "username $id",
            name = "name $id",
            UnsplashUserProfileImage(
                small = "https://images.unsplash.com/profile-1665554136768-6615667f6670image?ixlib=rb-4.0.3&crop=faces&fit=crop&w=32&h=32",
                medium = "https://images.unsplash.com/profile-1665554136768-6615667f6670image?ixlib=rb-4.0.3&crop=faces&fit=crop&w=64&h=64",
                large = "https://images.unsplash.com/profile-1665554136768-6615667f6670image?ixlib=rb-4.0.3&crop=faces&fit=crop&w=128&h=128"
            )
        )
    )
}

