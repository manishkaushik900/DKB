package com.manish.dkb.data.source

import com.manish.dkb.data.remote.ApiService
import com.manish.dkb.data.remote.models.AlbumDtoItem
import retrofit2.Response
import javax.inject.Inject

/*Network data source to fetch data from server using api service client*/
class NetworkAlbumDataSource @Inject constructor(
    private val apiService: ApiService
) {
//    Get Album list from server
    suspend fun getAlbumList(): Response<List<AlbumDtoItem>> = apiService.getAlbumList()

//    get Album detail by photo Id
    suspend fun getAlbumById(id: Int): Response<AlbumDtoItem> = apiService.getAlbumById(id)

}