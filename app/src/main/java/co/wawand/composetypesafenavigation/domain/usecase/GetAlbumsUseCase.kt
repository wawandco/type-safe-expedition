package co.wawand.composetypesafenavigation.domain.usecase

import android.util.Log
import co.wawand.composetypesafenavigation.core.util.Resource
import co.wawand.composetypesafenavigation.domain.model.Album
import co.wawand.composetypesafenavigation.domain.repository.AlbumRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAlbumsUseCase @Inject constructor(
    private val albumRepository: AlbumRepository
) {

    operator fun invoke(): Flow<Resource<List<Album>>> = flow {
        emit(Resource.Loading())
        runCatching {
            albumRepository.retrieveRemoteAlbums()
        }.onFailure {
            it.printStackTrace()
            emit(Resource.Error(it.message.toString()))
        }.onSuccess {
            it.collect { result ->
                if (result is Resource.Success) {
                    albumRepository.retrieveLocalAlbums().collect { result ->
                        emit(Resource.Success(result.data))
                    }
                } else {
                    emit(Resource.Success(emptyList()))
                    Log.d("GetAlbumsUseCase", "fail: ${result.data}")
                }
            }
        }
    }
}