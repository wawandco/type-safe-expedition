package co.wawand.composetypesafenavigation.data.repository

import android.util.Log
import co.wawand.composetypesafenavigation.core.Constant.GENERIC_ERROR
import co.wawand.composetypesafenavigation.core.util.Resource
import co.wawand.composetypesafenavigation.data.local.database.dao.AuthorDao
import co.wawand.composetypesafenavigation.data.mapper.toDBEntity
import co.wawand.composetypesafenavigation.data.mapper.toDomain
import co.wawand.composetypesafenavigation.data.remote.api.AuthorAPIService
import co.wawand.composetypesafenavigation.data.remote.api.entity.AuthorAPIEntity
import co.wawand.composetypesafenavigation.domain.model.Author
import co.wawand.composetypesafenavigation.domain.repository.AuthorRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuthorRepositoryImpl @Inject constructor(
    private val authorAPIService: AuthorAPIService,
    private val authorDao: AuthorDao
) : AuthorRepository {
    override suspend fun retrieveRemoteAuthors(): Flow<Resource<Boolean>> = flow {
        emit(Resource.Loading())

        runCatching {
            authorAPIService.getAuthors()
        }.onFailure {
            it.printStackTrace()
            emit(Resource.Error(it.message ?: GENERIC_ERROR))
        }.onSuccess { response ->
            if (response.isSuccessful) {
                response.body()?.let { remoteAuthors ->
                    saveAuthors(remoteAuthors)?.let {
                        emit(Resource.Error(it))
                    } ?: emit(Resource.Success(true))
                }
            } else {
                emit(Resource.Success(false))
            }
        }
    }

    override suspend fun retrieveLocalAuthors(): Flow<Resource<List<Author>>> =
        flow {
            emit(Resource.Loading())

            runCatching {
                authorDao.getAuthors()
            }.onFailure {
                it.printStackTrace()
                emit(Resource.Error(it.message ?: GENERIC_ERROR))
            }.onSuccess { localAuthors ->
                emit(Resource.Success(data = localAuthors.map { it.toDomain() }))
            }
        }

    override suspend fun persistAuthor(author: Author): Flow<Resource<Boolean>> {
        TODO("Not yet implemented")
    }

    override suspend fun getAuthorDetails(id: Long): Flow<Resource<Author>> {
        TODO("Not yet implemented")
    }

    private fun saveAuthors(remoteAuthors: List<AuthorAPIEntity>): String? {
        return try {
            authorDao.insertAuthors(remoteAuthors.map { it.toDBEntity() })
            null
        } catch (e: Exception) {
            e.printStackTrace()
            Log.d("AuthorRepository", "saveAuthors error: ${e.message}")
            e.message
        }
    }
}