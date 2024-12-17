package co.wawand.composetypesafenavigation.domain.usecase

import android.util.Log
import co.wawand.composetypesafenavigation.core.Constant.GENERIC_ERROR
import co.wawand.composetypesafenavigation.core.util.Resource
import co.wawand.composetypesafenavigation.domain.model.User
import co.wawand.composetypesafenavigation.domain.repository.UserPreferencesRepository
import co.wawand.composetypesafenavigation.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SignedInUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) {
    operator fun invoke(user: User): Flow<Resource<Boolean>> = flow {
        emit(Resource.Loading())

        runCatching {
            userRepository.persistUser(user)
        }.onFailure {
            Log.d("SignedInUseCase", "onFailure: ${it.message}")
            it.printStackTrace()
            emit(Resource.Error(it.message.toString()))
        }.onSuccess {
            it.collect { result ->
                when (result) {
                    is Resource.Loading -> emit(Resource.Loading())
                    is Resource.Error -> emit(Resource.Error(result.message ?: GENERIC_ERROR))
                    is Resource.Success -> {
                        result.data?.let { userId ->
                            persistUserId(userId).let { errorMessage ->
                                if (errorMessage != null) {
                                    emit(Resource.Error(errorMessage))
                                } else {
                                    emit(Resource.Success(true))
                                }
                            }
                        } ?: emit(Resource.Error(result.message ?: GENERIC_ERROR))
                    }
                }
            }
        }
    }

    private suspend fun persistUserId(userId: Long): String? {
        return try {
            userPreferencesRepository.setUserId(userId)
            null
        } catch (e: Exception) {
            e.printStackTrace()
            e.message
        }
    }
}