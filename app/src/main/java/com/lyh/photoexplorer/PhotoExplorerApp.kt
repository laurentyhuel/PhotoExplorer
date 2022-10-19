package com.lyh.photoexplorer

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.disk.DiskCache
import coil.memory.MemoryCache
import com.lyh.photoexplorer.data.di.getDataModule
import com.lyh.photoexplorer.domain.di.domainModule
import com.lyh.photoexplorer.feature.core.R
import com.lyh.photoexplorer.feature.photos.di.featurePhotosModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class PhotoExplorerApp : Application(), ImageLoaderFactory {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        startKoin {
            androidContext(this@PhotoExplorerApp)
            modules(
                getDataModule(BuildConfig.DEBUG),
                domainModule,
                featurePhotosModule,
            )
        }
    }

    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(this)
            .placeholder(R.drawable.ic_no_image)
            .error(R.drawable.ic_no_image)
            .crossfade(true)
            .memoryCache {
                MemoryCache.Builder(this)
                    .maxSizePercent(0.25)
                    .build()
            }
            .diskCache {
                DiskCache.Builder()
                    .directory(this.cacheDir.resolve("image_cache"))
                    .maxSizePercent(0.02)
                    .build()
            }
            .build()
    }
}
