package com.manish.dkb.presentation.ui.compose

import com.manish.dkb.data.remote.models.AlbumDtoItem


val albumDtoSample = AlbumDtoItem(
    albumId = 1,
    id = 1,
    title = "accusamus beatae ad facilis cum similique qui sunt",
    url ="https://via.placeholder.com/600/92c952",
    thumbnailUrl ="https://via.placeholder.com/600/92c952"
)

 fun listOfSamples(): MutableList<AlbumDtoItem> {
    val sampleList = mutableListOf<AlbumDtoItem>()

    for (i in 1..10){
        sampleList.add(albumDtoSample.copy(id = i))
    }
     return sampleList

}

val albumList = listOf(
    albumDtoSample,
    albumDtoSample.copy(id = 2),
    albumDtoSample.copy(id = 3),
    albumDtoSample.copy(id = 4),
    albumDtoSample.copy(id = 5),
    albumDtoSample.copy(id = 6),
    albumDtoSample.copy(id = 7)
)

