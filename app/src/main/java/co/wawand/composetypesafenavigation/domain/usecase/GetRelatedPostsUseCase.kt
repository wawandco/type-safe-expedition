package co.wawand.composetypesafenavigation.domain.usecase

import co.wawand.composetypesafenavigation.core.Constant.GENERIC_ERROR
import co.wawand.composetypesafenavigation.core.util.Resource
import co.wawand.composetypesafenavigation.domain.model.Post
import co.wawand.composetypesafenavigation.domain.repository.PostRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetRelatedPostsUseCase @Inject constructor(
    private val postRepository: PostRepository
) {
    operator fun invoke(userId: Long): Flow<Resource<List<Post>>> = flow {
        emit(Resource.Loading())

        runCatching {
            postRepository.getPostsByUserId(userId)
        }.onFailure {
            it.printStackTrace()
            emit(Resource.Error(it.message ?: GENERIC_ERROR))
        }.onSuccess {
            it.collect { result ->
                if (result is Resource.Success) {
                    emit(result)
                } else {
                    emit(Resource.Error(result.message ?: GENERIC_ERROR))
                }
            }
        }
    }
}