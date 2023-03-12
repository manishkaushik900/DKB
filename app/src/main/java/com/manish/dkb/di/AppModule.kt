package com.manish.dkb.di

import android.content.Context
import com.google.gson.GsonBuilder
import com.manish.dkb.BuildConfig
import com.manish.dkb.MainApplication
import com.manish.dkb.data.remote.ApiService
import com.manish.dkb.data.source.NetworkAlbumDataSource
import com.manish.dkb.utils.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context): MainApplication =
        app as MainApplication

    @Singleton
    @Provides
    fun provideContext(@ApplicationContext app: Context): Context = app.applicationContext

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .build()

    private val READ_TIMEOUT = 30
    private val WRITE_TIMEOUT = 30
    private val CONNECTION_TIMEOUT = 10

    @Provides
    @Singleton
    fun provideCacheInterceptor(): NetworkCacheInterceptor = NetworkCacheInterceptor()

    @Provides
    @Singleton
    fun provideForceCacheInterceptor(networkConnectivity: NetworkConnectivity): OfflineCacheInterceptor =
        OfflineCacheInterceptor(networkConnectivity.isNetworkAvailable())

    @Provides
    @Singleton
    fun provideOkHttpClient(
        @ApplicationContext context: Context,
        cacheInterceptor: NetworkCacheInterceptor,
        forceCacheInterceptor: OfflineCacheInterceptor
    ): OkHttpClient = OkHttpClient().newBuilder()
        .cache(Cache(context.cacheDir, 10L * 1024L * 1024L))// 10 MiB
        .addNetworkInterceptor(cacheInterceptor)// only if Cache-Control header is not enabled from the server
        .addInterceptor(forceCacheInterceptor)
        .connectTimeout(CONNECTION_TIMEOUT.toLong(), TimeUnit.SECONDS)
        .readTimeout(READ_TIMEOUT.toLong(), TimeUnit.SECONDS)
        .writeTimeout(WRITE_TIMEOUT.toLong(), TimeUnit.SECONDS)
        .build()


    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Singleton
    @Provides
    fun providesNetworkDataSource(apiService: ApiService): NetworkAlbumDataSource =
        NetworkAlbumDataSource(apiService)

    @Provides
    @Singleton
    @DispatcherIO
    fun provideIoDispatcher(): CoroutineContext = Dispatchers.IO

    @Provides
    @Singleton
    @DispatcherMain
    fun provideMainDispatcher(): CoroutineContext = Dispatchers.Main

    @Provides
    @Singleton
    @DispatcherDefault
    fun provideDefaultDispatcher(): CoroutineContext = Dispatchers.Default


    @Provides
    @Singleton
    fun provideNetworkConnectivity(@ApplicationContext context: Context): NetworkConnectivity =
        Network(context)
}