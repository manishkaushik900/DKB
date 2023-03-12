package com.manish.dkb.data.source

import com.manish.dkb.data.mappers.toDomainAlbumItem
import com.manish.dkb.data.mappers.toDomainAlbumList
import com.manish.dkb.data.remote.ApiService
import com.manish.dkb.domain.models.AlbumItem
import javax.inject.Inject

/*Network data source to fetch data from server using api service client*/
class NetworkAlbumDataSource @Inject constructor(
    private val apiService: ApiService
) {
//    Get Album list from server
    suspend fun getAlbumList(): List<AlbumItem> = apiService.getAlbumList().toDomainAlbumList()

//    get Album detail by photo Id
    suspend fun getAlbumById(id: Int): AlbumItem = apiService.getAlbumById(id).toDomainAlbumItem()

}