package com.manish.dkb

import com.manish.dkb.data.remote.models.AlbumDtoItem


val getAlbumDetailsSuccessResponse = """
                       {
                       "albumId": 1,"id": 10,"title": "accusamus beatae ad facilis cum similique qui sunt", "url": "https://via.placeholder.com/600/92c952",
  "thumbnailUrl": "https://via.placeholder.com/150/92c952"}""".trimIndent()

val getAlbumListSuccessResponse = """
           [
  {
    "albumId": 1,
    "id": 1,
    "title": "accusamus beatae ad facilis cum similique qui sunt",
    "url": "https://via.placeholder.com/600/92c952",
    "thumbnailUrl": "https://via.placeholder.com/150/92c952"
  },
  {
    "albumId": 1,
    "id": 2,
    "title": "reprehenderit est deserunt velit ipsam",
    "url": "https://via.placeholder.com/600/771796",
    "thumbnailUrl": "https://via.placeholder.com/150/771796"
  }]
""".trimIndent()


val getAlbumDetailsFailureResponse = """{}""".trimIndent()

val getAlbumListFailureResponse = """{}""".trimIndent()

val item1 = AlbumDtoItem(1,1, title = "test", thumbnailUrl = "https://via.placeholder.com/600/92c952",
    url = "https://via.placeholder.com/150/92c952")
val item2 = AlbumDtoItem(2,2, title = "test2", thumbnailUrl = "https://via.placeholder.com/600/92c9522",
    url = "https://via.placeholder.com/150/92c9523")
val item3= AlbumDtoItem(3,3, title = "test3", thumbnailUrl = "https://via.placeholder.com/600/92c9523",
    url = "https://via.placeholder.com/150/92c9523")
val dummyAlbumListData = mutableListOf<AlbumDtoItem>(item1, item2, item3)