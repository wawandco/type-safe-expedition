package co.wawand.composetypesafenavigation.domain.usecase

import co.wawand.composetypesafenavigation.core.util.Resource
import co.wawand.composetypesafenavigation.domain.model.Album
import co.wawand.composetypesafenavigation.domain.repository.AlbumRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetLocalAlbumsUseCase @Inject constructor(
    private val albumsRepository: AlbumRepository
) {

    operator fun invoke(): Flow<Resource<List<Album>>> = flow {
        emit(Resource.Loading())

        runCatching {
            albumsRepository.retrieveLocalAlbums()
        }.onFailure {
            emit(Resource.Error(it.message.toString()))
        }.onSuccess {
            it.collect { result ->
                if (result is Resource.Success) {
                    emit(Resource.Success(result.data))
                } else {
                    emit(Resource.Error(result.message.toString()))
                }
            }
        }
    }
}