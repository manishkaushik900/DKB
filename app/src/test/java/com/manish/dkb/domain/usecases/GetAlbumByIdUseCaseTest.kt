package com.manish.dkb.domain.usecases

import com.manish.dkb.domain.repository.AlbumRepository
import com.manish.dkb.item1
import com.manish.dkb.item2
import com.manish.dkb.utils.Resource
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.assertj.core.api.Assertions
import org.junit.After
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GetAlbumByIdUseCaseTest {

    @MockK(relaxUnitFun = true, relaxed = true)
    private lateinit var repo: AlbumRepository

    private lateinit var getAlbumDetailUseCase: GetAlbumByIdUseCase

    @ExperimentalCoroutinesApi
    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true, relaxUnitFun = true)

        getAlbumDetailUseCase = GetAlbumByIdUseCase(repo, Dispatchers.IO)

        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun `successfully fetches albums Detail`() = runTest {
        coEvery {
            repo.getAlbumById(5)
        } returns Resource.Success(item1)

        val response = getAlbumDetailUseCase.execute(5)

        Assertions.assertThat(response.data?.id).isEqualTo(1)
        Assertions.assertThat(response.data).isEqualTo(item1)
    }

    @Test
    fun `successfully fetches albums Detail but data not matches`() = runTest {
        coEvery {
            repo.getAlbumById(5)
        } returns Resource.Success(item1)

        val response = getAlbumDetailUseCase.execute(5)

        Assertions.assertThat(response.data?.id).isNotEqualTo(2)
        Assertions.assertThat(response.data).isNotEqualTo(item2)
    }

    @Test
    fun `fail to  fetch albums Detail`() = runTest {
        coEvery {
            repo.getAlbumById(5)
        } returns Resource.Error("Something Went Wrong")

        val response = getAlbumDetailUseCase.execute(5)

        Assertions.assertThat(response.data).isNull()
        Assertions.assertThat(response.message).isEqualTo("Something Went Wrong")
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

}