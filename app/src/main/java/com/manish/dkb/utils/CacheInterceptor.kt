package com.manish.dkb.utils

import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.util.concurrent.TimeUnit

// used to add the interceptor at the network level.
class NetworkCacheInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val response: Response = chain.proceed(chain.request())
        val cacheControl = CacheControl.Builder()
            .maxAge(10, TimeUnit.DAYS)
            .build()
        return response.newBuilder()
            .header("Cache-Control", cacheControl.toString())
            .removeHeader("Pragma")
            .build()
    }
}

//used to add the interceptor at the application level.
class OfflineCacheInterceptor(private val isInternetAvailable: Boolean) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder: Request.Builder = chain.request().newBuilder()
        if (!isInternetAvailable) {
            builder.header("Cache-Control", "public, only-if-cached, max-stale=" + 60)
            builder.removeHeader("Pragma")
        }
        return chain.proceed(builder.build());
    }
}