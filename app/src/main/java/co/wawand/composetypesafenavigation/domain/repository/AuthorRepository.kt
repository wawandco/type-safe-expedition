package co.wawand.composetypesafenavigation.domain.repository

import co.wawand.composetypesafenavigation.core.util.Resource
import co.wawand.composetypesafenavigation.domain.model.Author
import kotlinx.coroutines.flow.Flow

interface AuthorRepository {

    suspend fun retrieveRemoteAuthors(): Flow<Resource<Boolean>>

    suspend fun retrieveLocalAuthors(): Flow<Resource<List<Author>>>

    suspend fun persistAuthor(author: Author): Flow<Resource<Boolean>>

    suspend fun getAuthorDetails(id: Long): Flow<Resource<Author>>
}