package com.manish.dkb.domain.usecases

import com.manish.dkb.data.remote.models.AlbumDtoItem
import com.manish.dkb.domain.repository.AlbumRepository
import com.manish.dkb.utils.Resource
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

/*Get album by photo id use case*/
class GetAlbumByIdUseCase @Inject constructor(
    private val repository: AlbumRepository,
    private val ioDispatcher: CoroutineContext
) {

    suspend fun execute(id: Int): Resource<AlbumDtoItem> {
        return withContext(ioDispatcher) {
            repository.getAlbumById(id)
        }
    }

}