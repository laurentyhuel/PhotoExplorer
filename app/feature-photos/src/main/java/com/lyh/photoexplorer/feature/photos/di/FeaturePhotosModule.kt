package com.lyh.photoexplorer.feature.photos.di

import com.lyh.photoexplorer.feature.photos.detail.PhotoViewModel
import com.lyh.photoexplorer.feature.photos.list.PhotoListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val featurePhotosModule = module {
    viewModel { PhotoViewModel(get(), get(), get(), get()) }
    viewModel { PhotoListViewModel(get()) }
}