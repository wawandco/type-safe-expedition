package co.wawand.composetypesafenavigation.domain.repository

import co.wawand.composetypesafenavigation.core.util.Resource
import co.wawand.composetypesafenavigation.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun persistUser(user: User): Flow<Resource<Long>>
}