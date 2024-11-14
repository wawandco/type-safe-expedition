package co.wawand.composetypesafenavigation.domain.usecase

import co.wawand.composetypesafenavigation.core.Constant.GENERIC_ERROR
import co.wawand.composetypesafenavigation.core.util.Resource
import co.wawand.composetypesafenavigation.domain.model.AlbumWithPhotos
import co.wawand.composetypesafenavigation.domain.repository.AlbumRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAlbumDetailsUseCase @Inject constructor(
    private val albumRepository: AlbumRepository,
) {
    operator fun invoke(albumId: Long): Flow<Resource<AlbumWithPhotos>> = flow {
        emit(Resource.Loading())

        runCatching {
            albumRepository.getAlbumDetailsWithPhotos(albumId)
        }.onFailure {
            it.printStackTrace()
            emit(Resource.Error(it.message ?: GENERIC_ERROR))
        }.onSuccess {
            it.collect { result ->
                when (result) {
                    is Resource.Loading -> emit(Resource.Loading())
                    is Resource.Error -> emit(Resource.Error(result.message ?: GENERIC_ERROR))
                    is Resource.Success -> emit(Resource.Success(result.data))
                }
            }
        }
    }
}