package com.manish.dkb.domain.usecases

import com.google.common.truth.Truth.assertThat
import com.manish.dkb.domain.repository.AlbumRepository
import com.manish.dkb.dummyAlbumListData
import com.manish.dkb.item1
import com.manish.dkb.utils.Resource
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GetAlbumsUseCaseTest {
    @MockK(relaxUnitFun = true, relaxed = true)
    private lateinit var repo: AlbumRepository

    private lateinit var getAlbumListUseCase: GetAlbumsUseCase

    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true, relaxUnitFun = true)

        getAlbumListUseCase = GetAlbumsUseCase(repo, testDispatcher)

        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun `successfully fetches albums list`() = runTest {
        coEvery {
            repo.getAlbumList()
        } returns Resource.Success(dummyAlbumListData)

        val response = getAlbumListUseCase.execute()

        assertThat(response.data?.size).isEqualTo(3)
        assertThat(response.data?.get(0)).isEqualTo(item1)
    }

    @Test
    fun `fail to  fetch albums list`() = runTest {
        coEvery {
            repo.getAlbumList()
        } returns Resource.Error("Something Went Wrong")

        val response = getAlbumListUseCase.execute()

        assertThat(response.data).isNull()
        assertThat(response.message).isEqualTo("Something Went Wrong")
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

}