package co.wawand.composetypesafenavigation.data.repository

import co.wawand.composetypesafenavigation.core.util.Resource
import co.wawand.composetypesafenavigation.data.local.database.dao.UserDao
import co.wawand.composetypesafenavigation.data.mapper.toDBEntity
import co.wawand.composetypesafenavigation.domain.model.User
import co.wawand.composetypesafenavigation.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDao: UserDao,
) : UserRepository {
    override suspend fun persistUser(user: User): Flow<Resource<Long>> = flow {
        emit(Resource.Loading())

        runCatching {
            userDao.upsertUser(user.toDBEntity())
        }.onFailure {
            it.printStackTrace()
            emit(Resource.Error(it.message.toString()))
            emit(Resource.Success(0L))
        }.onSuccess {
            emit(Resource.Success(it))
        }
    }

}