package co.wawand.composetypesafenavigation.domain.usecase

import co.wawand.composetypesafenavigation.core.util.Resource
import co.wawand.composetypesafenavigation.domain.model.Post
import co.wawand.composetypesafenavigation.domain.repository.PostRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetPostsUseCase @Inject constructor(
    private val postRepository: PostRepository
) {

    operator fun invoke(): Flow<Resource<List<Post>>> = flow {
        emit(Resource.Loading())

        runCatching {
            postRepository.retrieveRemotePosts()
        }.onFailure {
            it.printStackTrace()
            emit(Resource.Error(it.message.toString()))
        }.onSuccess {
            it.collect { result ->
                if (result is Resource.Success) {
                    postRepository.retrieveLocalPosts().collect { posts ->
                        emit(posts)
                    }
                } else {
                    emit(Resource.Error(result.message.toString()))
                }
            }
        }
    }
}