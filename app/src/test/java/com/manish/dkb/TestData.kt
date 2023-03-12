package com.manish.dkb

import com.manish.dkb.domain.models.AlbumItem


val getAlbumDetailsSuccessResponse = """
                       {
                       "albumId": 1,"id": 10,"title": "accusamus beatae ad facilis cum similique qui sunt", "url": "https://via.placeholder.com/600/92c952",
  "thumbnailUrl": "https://via.placeholder.com/150/92c952"}""".trimIndent()

val getAlbumListSuccessResponse = """
           [
  {
    "albumId": 1,
    "title": "accusamus beatae ad facilis cum similique qui sunt",
    "url": "https://via.placeholder.com/600/92c952",
    "thumbnailUrl": "https://via.placeholder.com/150/92c952"
  },
  {
    "albumId": 1,
    "title": "reprehenderit est deserunt velit ipsam",
    "url": "https://via.placeholder.com/600/771796",
    "thumbnailUrl": "https://via.placeholder.com/150/771796"
  }]
""".trimIndent()

val expectedResult: List<AlbumItem>
    get() = listOf(item1, item2)


val getAlbumDetailsFailureResponse = """{}""".trimIndent()

val getAlbumListFailureResponse = """{}""".trimIndent()

val item1 = AlbumItem(1, title = "accusamus beatae ad facilis cum similique qui sunt", thumbnailUrl = "https://via.placeholder.com/150/92c952",
    url = "https://via.placeholder.com/600/92c952")
val item2 = AlbumItem(2, title = "reprehenderit est deserunt velit ipsam", thumbnailUrl = "https://via.placeholder.com/150/771796",
    url = "https://via.placeholder.com/600/771796")
val item3= AlbumItem(3, title = "test3", thumbnailUrl = "https://via.placeholder.com/600/92c9523",
    url = "https://via.placeholder.com/150/92c9523")
val dummyAlbumListData = mutableListOf<AlbumItem>(item1, item2, item3)