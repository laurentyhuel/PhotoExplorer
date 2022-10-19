package com.lyh.photoexplorer.data.remote.di

import android.content.Context
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.lyh.photoexplorer.data.remote.BuildConfig
import com.lyh.photoexplorer.data.remote.UnsplashApi
import com.lyh.photoexplorer.data.remote.core.ResultCallAdapterFactory
import kotlinx.serialization.json.Json
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import org.koin.dsl.module
import retrofit2.Retrofit

/**
 * Koin module for data-remote
 */
fun getDataRemoteModule(isDebugEnabled: Boolean) = module {

    // Retrofit and OkHttp setup
    single { Json { ignoreUnknownKeys = true } }
    single { providesOkHttpClient(get(), isDebugEnabled) }
    single { providesRetrofit(get(), get()) }

    // API
    single { providesUnsplashApi(get()) }
}

private fun providesUnsplashApi(retrofit: Retrofit): UnsplashApi =
    retrofit.create(UnsplashApi::class.java)

private fun providesRetrofit(jsonSerializer: Json, okHttpClient: OkHttpClient) = Retrofit.Builder()
    .baseUrl(baseUrl)
    .client(okHttpClient)
    .addConverterFactory(jsonSerializer.asConverterFactory(mediaTypeJson.toMediaType()))
    .addCallAdapterFactory(ResultCallAdapterFactory())
    .build()

private fun providesOkHttpClient(context: Context, isDebugEnabled: Boolean): OkHttpClient =
    OkHttpClient()
        .newBuilder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = if (isDebugEnabled)
                Level.BODY
            else
                Level.NONE
        })
        .addInterceptor(unsplashAuthentInterceptor())
        .cache(Cache(context.cacheDir, apiCacheSize))
        .build()

private fun unsplashAuthentInterceptor() = Interceptor { chain ->
    // Request customization: add request headers
    val authenticatedRequest = chain.request().newBuilder()
        .addHeader("Authorization", "Client-ID ${BuildConfig.ACCESS_KEY}")
        .addHeader("Accept-Version", "v1")
        .build()

    return@Interceptor chain.proceed(authenticatedRequest)
}

private const val baseUrl = "https://api.unsplash.com/"
private const val mediaTypeJson = "application/json"
private const val apiCacheSize = 10 * 1024 * 1024L // 10 MB of cache




