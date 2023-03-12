package com.manish.dkb.domain.repository

import com.manish.dkb.domain.models.AlbumItem
import com.manish.dkb.domain.util.Resource

interface AlbumRepository {

    suspend fun getAlbumList(): Resource<List<AlbumItem>>

    suspend fun getAlbumById(id: Int): Resource<AlbumItem>
}