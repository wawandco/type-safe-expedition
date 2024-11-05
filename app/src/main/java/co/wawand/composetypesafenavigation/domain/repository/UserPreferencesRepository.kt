package co.wawand.composetypesafenavigation.domain.repository

import co.wawand.composetypesafenavigation.core.util.Resource
import kotlinx.coroutines.flow.Flow

interface UserPreferencesRepository {

    suspend fun setUserId(userId: Long): Flow<Resource<Boolean>>

    suspend fun getUserId(): Flow<Resource<Long>>
}