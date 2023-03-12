package com.manish.dkb.data.repository

import com.manish.dkb.data.source.NetworkAlbumDataSource
import com.manish.dkb.domain.models.AlbumItem
import com.manish.dkb.domain.repository.AlbumRepository
import com.manish.dkb.utils.NetworkConnectivity
import com.manish.dkb.domain.util.Resource
import javax.inject.Inject

/*Implementation of Album Repository*/
class AlbumRepositoryImpl @Inject constructor(
    private val networkDataSource: NetworkAlbumDataSource, private val network: NetworkConnectivity
) : AlbumRepository {

    /*get album list from network data source*/
    override suspend fun getAlbumList(): Resource<List<AlbumItem>> {
        return try {
            val result = networkDataSource.getAlbumList()

            if (result.isNotEmpty()) {
                Resource.Success(result)
            } else {
                Resource.Error(IllegalStateException("Empty product list").message.toString())
            }

        } catch (e: Exception) {
            Resource.Error(e.message.toString())
        }

    }

    /*get album by photo id from network data source*/
    override suspend fun getAlbumById(id: Int): Resource<AlbumItem> {
        return try {
            val result = networkDataSource.getAlbumById(id)

            Resource.Success(result)

        } catch (e: Exception) {
            Resource.Error(e.message.toString())
        }
    }
}