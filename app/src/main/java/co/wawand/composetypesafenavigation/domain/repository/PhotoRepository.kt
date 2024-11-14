package co.wawand.composetypesafenavigation.domain.repository

import co.wawand.composetypesafenavigation.core.util.Resource
import co.wawand.composetypesafenavigation.domain.model.Photo
import kotlinx.coroutines.flow.Flow

interface PhotoRepository {

    suspend fun getPhotoDetails(id: Long): Flow<Resource<Photo>>
}