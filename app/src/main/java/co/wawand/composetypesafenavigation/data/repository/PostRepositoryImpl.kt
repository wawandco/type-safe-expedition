package co.wawand.composetypesafenavigation.data.repository

import co.wawand.composetypesafenavigation.core.util.Resource
import co.wawand.composetypesafenavigation.data.local.database.dao.PostDao
import co.wawand.composetypesafenavigation.data.mapper.toDBEntity
import co.wawand.composetypesafenavigation.data.mapper.toDomain
import co.wawand.composetypesafenavigation.data.remote.api.PostAPIService
import co.wawand.composetypesafenavigation.domain.model.Post
import co.wawand.composetypesafenavigation.domain.repository.PostRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(
    private val postAPIService: PostAPIService, private val postDao: PostDao
) : PostRepository {

    override suspend fun retrieveRemotePosts(): Flow<Resource<Boolean>> = flow {
        emit(Resource.Loading())

        runCatching {
            postAPIService.getPosts()
        }.onFailure {
            it.printStackTrace()
            emit(Resource.Error(it.message.toString()))
        }.onSuccess { response ->
            if (response.isSuccessful) {
                response.body()?.let { posts ->
                    postDao.insertPosts(posts.map { it.toDBEntity() })
                }
                emit(Resource.Success(true))
            } else {
                emit(Resource.Success(false))
            }
        }

    }

    override suspend fun retrieveLocalPosts(): Flow<Resource<List<Post>>> = flow {
        emit(Resource.Loading())

        runCatching {
            postDao.getPosts().toDomain()
        }.onFailure {
            it.printStackTrace()
            emit(Resource.Error(it.message.toString()))
        }.onSuccess { posts ->
            emit(Resource.Success(posts))
        }
    }

    override suspend fun getPostDetails(id: Long): Flow<Resource<Post>> = flow {
        emit(Resource.Loading())

        runCatching {
            postDao.getPostById(id).toDomain()
        }.onFailure {
            it.printStackTrace()
            emit(Resource.Error(it.message.toString()))
        }.onSuccess { post ->
            emit(Resource.Success(post))
        }
    }

    override suspend fun getPostsByUserId(userId: Long): Flow<Resource<List<Post>>> = flow {
        emit(Resource.Loading())

        runCatching {
            postDao.getPostsByAuthor(userId).toDomain()
        }.onFailure {
            it.printStackTrace()
            emit(Resource.Error(it.message.toString()))
        }.onSuccess { posts ->
            emit(Resource.Success(posts))
        }
    }

}