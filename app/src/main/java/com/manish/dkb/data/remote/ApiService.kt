package com.manish.dkb.data.remote

import com.manish.dkb.data.remote.models.AlbumDtoItem
import retrofit2.http.GET
import retrofit2.http.Path


interface ApiService {

    //    To get the list of albums from server
    @GET("/photos")
    suspend fun getAlbumList(): List<AlbumDtoItem>

    //    To get the particular album based on the photo ID
    @GET("/photos/{id}")
    suspend fun getAlbumById(
        @Path("id") id: Int,
    ): AlbumDtoItem
}