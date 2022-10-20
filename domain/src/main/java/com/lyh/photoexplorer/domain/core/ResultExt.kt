package com.lyh.photoexplorer.domain.core


inline fun <T, R> T.runCatchingOnResult(block: T.() -> Result<R>): Result<R> {
    return try {
        block()
    } catch (e: Throwable) {
        Result.failure(e)
    }
}