package co.wawand.composetypesafenavigation.domain.usecase

import co.wawand.composetypesafenavigation.core.Constant.GENERIC_ERROR
import co.wawand.composetypesafenavigation.core.util.Resource
import co.wawand.composetypesafenavigation.domain.model.Author
import co.wawand.composetypesafenavigation.domain.repository.AuthorRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetLocalAuthorsUseCase @Inject constructor(
    private val authorRepository: AuthorRepository
) {
    suspend operator fun invoke(): Flow<Resource<List<Author>>> = flow {
        emit(Resource.Loading())

        runCatching {
            authorRepository.retrieveLocalAuthors()
        }.onFailure {
            it.printStackTrace()
            emit(Resource.Error(it.message ?: GENERIC_ERROR))
        }.onSuccess {
            it.collect { result ->
                if (result is Resource.Success) {
                    emit(Resource.Success(result.data))
                } else {
                    emit(Resource.Error(result.message ?: GENERIC_ERROR))
                }
            }
        }
    }
}