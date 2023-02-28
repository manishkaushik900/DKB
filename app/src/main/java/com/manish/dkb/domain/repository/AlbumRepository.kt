package com.manish.dkb.domain.repository

import com.manish.dkb.data.remote.models.AlbumDtoItem
import com.manish.dkb.utils.Resource

interface AlbumRepository {

    suspend fun getAlbumList(): Resource<List<AlbumDtoItem>>

    suspend fun getAlbumById(id: Int): Resource<AlbumDtoItem>
}