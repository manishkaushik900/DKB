package com.manish.dkb.data.mappers

import com.manish.dkb.data.remote.models.AlbumDtoItem
import com.manish.dkb.domain.models.AlbumItem

fun AlbumDtoItem.toDomainAlbumItem() = AlbumItem(
    albumId = albumId,
    title = title,
    url = url,
    thumbnailUrl = thumbnailUrl

)

fun List<AlbumDtoItem>.toDomainAlbumList(): List<AlbumItem> {
    val mappedAlbum = ArrayList<AlbumItem>()
    forEach {
        mappedAlbum.add(AlbumItem(
            albumId = it.id,
            title = it.title,
            url = it.url,
            thumbnailUrl = it.thumbnailUrl

        ))
    }

    return mappedAlbum
}