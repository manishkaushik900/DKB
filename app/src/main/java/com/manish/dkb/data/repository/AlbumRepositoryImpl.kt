package com.manish.dkb.data.repository

import com.manish.dkb.data.remote.models.AlbumDtoItem
import com.manish.dkb.data.source.NetworkAlbumDataSource
import com.manish.dkb.domain.repository.AlbumRepository
import com.manish.dkb.utils.NetworkConnectivity
import com.manish.dkb.utils.Resource
import retrofit2.Response
import javax.inject.Inject

/*Implementation of Album Repository*/
class AlbumRepositoryImpl @Inject constructor(
    private val networkDataSource: NetworkAlbumDataSource, private val network: NetworkConnectivity
) : AlbumRepository {

    /*get album list from network data source*/
    override suspend fun getAlbumList(): Resource<List<AlbumDtoItem>> {
        return getResult { networkDataSource.getAlbumList() }
    }

    /*get album by photo id from network data source*/
    override suspend fun getAlbumById(id: Int): Resource<AlbumDtoItem> {
        return getResult { networkDataSource.getAlbumById(id) }
    }

    /*common high order function to get the result*/
    private suspend fun <T> getResult(call: suspend () -> Response<T>): Resource<T> {
        if (!network.isNetworkAvailable()) {
            return error("No Internet Connection")
        }

        try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) return Resource.Success(body)
            }
            return error(" ${response.code()} ${response.message()}")
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }

    /*common method to get the error*/
    private fun <T> error(message: String): Resource<T> {
        return Resource.Error("Error: $message")
    }

}