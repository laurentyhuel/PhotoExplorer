package com.lyh.photoexplorer.domain.di

import com.lyh.photoexplorer.domain.PhotoStatsUseCase
import com.lyh.photoexplorer.domain.PhotoUseCase
import com.lyh.photoexplorer.domain.PhotosUseCase
import com.lyh.photoexplorer.domain.UserPhotosUseCase
import org.koin.dsl.module

/**
 * Koin module for data-remote
 */
val domainModule = module {
    single { PhotosUseCase(get()) }
    single { PhotoStatsUseCase(get()) }
    single { PhotoUseCase(get()) }
    single { UserPhotosUseCase(get()) }
}