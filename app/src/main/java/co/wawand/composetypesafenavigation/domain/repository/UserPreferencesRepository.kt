package co.wawand.composetypesafenavigation.domain.repository

import co.wawand.composetypesafenavigation.core.util.Resource

interface UserPreferencesRepository {

    suspend fun setUserId(userId: Long): Resource<Boolean>

    suspend fun getUserId(): Resource<Long>
}