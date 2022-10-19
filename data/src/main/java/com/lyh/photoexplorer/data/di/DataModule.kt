package com.lyh.photoexplorer.data.di

import com.lyh.photoexplorer.data.PhotosRepository
import com.lyh.photoexplorer.data.StatsRepository
import com.lyh.photoexplorer.data.remote.di.getDataRemoteModule
import com.lyh.photoexplorer.domain.repository.IPhotosRepository
import com.lyh.photoexplorer.domain.repository.IStatsRepository
import org.koin.dsl.module

/**
 * Koin module for data
 */
fun getDataModule(isDebugEnabled: Boolean) = module {
    includes(getDataRemoteModule(isDebugEnabled))

    single<IPhotosRepository> { PhotosRepository(get()) }
    single<IStatsRepository> { StatsRepository(get()) }
}
