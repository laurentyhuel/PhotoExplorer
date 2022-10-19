package com.lyh.photoexplorer.data.remote

import com.lyh.photoexplorer.data.remote.dto.UnsplashPhoto
import com.lyh.photoexplorer.data.remote.dto.UnsplashPhotoStats
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface UnsplashApi {

    /**
     * Use perPage = 21 because UI grid has 3 columns => 7 full rows
     */
    @GET("/photos")
    suspend fun getPhotos(
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = 21
    ): Result<List<UnsplashPhoto>>

    @GET("/photos/{photoId}")
    suspend fun getPhoto(@Path("photoId") photoId: String): Result<UnsplashPhoto>

    @GET("/users/{userId}/photos")
    suspend fun getUserPhotos(
        @Path("userId") userId: String,
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = 10
    ): Result<List<UnsplashPhoto>>

    @GET("photos/{photoId}/statistics")
    suspend fun getPhotoStats(
        @Path("photoId") photoId: String,
        @Query("quantity") quantity: Int = 1
    ): Result<UnsplashPhotoStats>
}

