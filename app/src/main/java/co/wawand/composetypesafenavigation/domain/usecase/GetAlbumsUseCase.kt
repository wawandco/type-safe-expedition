package co.wawand.composetypesafenavigation.domain.usecase

import android.util.Log
import co.wawand.composetypesafenavigation.core.Constant.GENERIC_ERROR
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
            Log.d("GetAlbumsUseCase", "retrieveRemoteAlbums error: ${it.message}")
            it.printStackTrace()
            emit(Resource.Error(it.message ?: GENERIC_ERROR))
        }.onSuccess {
            it.collect { retrieveRemoteAlbumsResult ->
                when (retrieveRemoteAlbumsResult) {
                    is Resource.Loading -> emit(Resource.Loading())
                    is Resource.Error -> emit(
                        Resource.Error(
                            retrieveRemoteAlbumsResult.message ?: GENERIC_ERROR
                        )
                    )

                    is Resource.Success -> {
                        if (retrieveRemoteAlbumsResult.data == true) {
                            runCatching {
                                albumRepository.retrieveRemotePhotos()
                            }.onFailure { retrieveRemotePhotosOnFailure ->
                                Log.d(
                                    "GetAlbumsUseCase",
                                    "retrieveRemotePhotos error: ${retrieveRemotePhotosOnFailure.message}"
                                )
                                retrieveRemotePhotosOnFailure.printStackTrace()
                                emit(
                                    Resource.Error(
                                        retrieveRemotePhotosOnFailure.message ?: GENERIC_ERROR
                                    )
                                )
                            }.onSuccess { retrieveRemotePhotosOnSuccess ->
                                retrieveRemotePhotosOnSuccess.collect { retrieveRemotePhotosResult ->
                                    when (retrieveRemotePhotosResult) {
                                        is Resource.Loading -> emit(Resource.Loading())
                                        is Resource.Error -> emit(
                                            Resource.Error(
                                                retrieveRemotePhotosResult.message ?: GENERIC_ERROR
                                            )
                                        )

                                        is Resource.Success -> {
                                            if (retrieveRemotePhotosResult.data == true) {
                                                albumRepository.retrieveLocalAlbums()
                                                    .collect { localAlbumsResult ->
                                                        when (localAlbumsResult) {
                                                            is Resource.Loading -> emit(Resource.Loading())
                                                            is Resource.Error -> emit(
                                                                Resource.Error(
                                                                    localAlbumsResult.message
                                                                        ?: GENERIC_ERROR
                                                                )
                                                            )

                                                            is Resource.Success -> emit(
                                                                Resource.Success(
                                                                    localAlbumsResult.data
                                                                )
                                                            )
                                                        }
                                                    }
                                            }
                                        }
                                    }
                                }
                            }
                        } else {
                            emit(Resource.Success(emptyList()))
                        }
                    }
                }
            }
        }
    }
}