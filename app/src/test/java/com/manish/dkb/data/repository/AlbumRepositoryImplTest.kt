package com.manish.dkb.data.repository

import com.manish.dkb.*
import com.manish.dkb.data.FakeNetworkConnectivity
import com.manish.dkb.data.remote.ApiService
import com.manish.dkb.data.source.NetworkAlbumDataSource
import com.manish.dkb.utils.NetworkConnectivity
import io.mockk.MockKAnnotations
import kotlinx.coroutines.test.runTest
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.assertj.core.api.Assertions
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class AlbumRepositoryImplTest{
    private val mockWebServer = MockWebServer()

    private lateinit var albumRepo: AlbumRepositoryImpl
    private lateinit var dataSource: NetworkAlbumDataSource
    private lateinit var networkConnectivity: NetworkConnectivity

    private val client = OkHttpClient.Builder()
        .connectTimeout(1, TimeUnit.SECONDS)
        .readTimeout(1, TimeUnit.SECONDS)
        .writeTimeout(1, TimeUnit.SECONDS)
        .build()

    private var apiService: ApiService = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(mockWebServer.url("/"))
        .client(client)
        .build()
        .create(ApiService::class.java)

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
        networkConnectivity = FakeNetworkConnectivity()
        dataSource = NetworkAlbumDataSource(apiService)
        albumRepo = AlbumRepositoryImpl(dataSource, networkConnectivity)
    }

    @Test
    fun `successfully fetches album list return success response`() = runTest {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(getAlbumListSuccessResponse)
        )

        val response = albumRepo.getAlbumList()

        Assertions.assertThat(response.data?.get(0)?.id).isEqualTo(1)
    }

    @Test
    fun `successfully fetches album Details return success response`() = runTest {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(getAlbumDetailsSuccessResponse)
        )

        val response = albumRepo.getAlbumById(5)

        Assertions.assertThat(response.data?.id).isEqualTo(10)
    }

    @Test
    fun `fail to fetches album list return null data`() = runTest {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(getAlbumListFailureResponse),
        )

        val response = albumRepo.getAlbumList()

        Assertions.assertThat(response.data).isNull()
    }

    @Test
    fun `fail to fetches album Detail return null data`() = runTest {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(500)
                .setBody(getAlbumDetailsFailureResponse),
        )

        val response = albumRepo.getAlbumById(5)

        Assertions.assertThat(response.data?.id).isNull()
        Assertions.assertThat(response.data?.albumId).isNull()
        Assertions.assertThat(response.data?.thumbnailUrl).isNull()
        Assertions.assertThat(response.data?.title).isNull()
        Assertions.assertThat(response.data?.url).isNull()
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }
}