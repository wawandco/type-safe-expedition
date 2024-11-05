package co.wawand.composetypesafenavigation.domain.repository

import co.wawand.composetypesafenavigation.core.util.Resource
import co.wawand.composetypesafenavigation.domain.model.Post
import kotlinx.coroutines.flow.Flow

interface PostRepository {

    suspend fun retrieveRemotePosts(): Flow<Resource<Boolean>>

    suspend fun retrieveLocalPosts(): Flow<Resource<List<Post>>>

    suspend fun getPostDetails(id: Long): Flow<Resource<Post>>

    suspend fun getPostsByUserId(userId: Long): Flow<Resource<List<Post>>>
}