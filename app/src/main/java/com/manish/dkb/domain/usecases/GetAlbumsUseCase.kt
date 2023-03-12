package com.manish.dkb.domain.usecases

import com.manish.dkb.di.DispatcherIO
import com.manish.dkb.domain.models.AlbumItem
import com.manish.dkb.domain.repository.AlbumRepository
import com.manish.dkb.domain.util.Resource
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

/*Get album list use case*/
class GetAlbumsUseCase @Inject constructor(
    private val repository: AlbumRepository,
    @DispatcherIO private val ioDispatcher: CoroutineContext
) {

    suspend fun execute(): Resource<List<AlbumItem>> {
        return withContext(ioDispatcher) {
            repository.getAlbumList()
        }
    }


//    suspend fun execute(): Flow<Resource<List<AlbumDtoItem>>> = flow {
//        emit(repository.getAlbumList())
//    }.flowOn(ioDispatcher)

}