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
        }.onSuccess {
            it.collect { result ->
                if (result is Resource.Success) {
                    result.data?.let { userId ->
                        Log.d("SignedInUseCase", "invoke: $userId")
                        persistUserId(userId).collect { success ->
                            emit(success)
                        }
                    }
                    emit(Resource.Error(GENERIC_ERROR))
                }
            }
        }.onFailure {
            it.printStackTrace()
            emit(Resource.Error(it.message.toString()))
        }
    }

    private fun persistUserId(userId: Long): Flow<Resource<Boolean>> = flow {
        emit(Resource.Loading())

        runCatching {
            userPreferencesRepository.setUserId(userId)
        }.onSuccess {
            it.collect { result ->
                if (result is Resource.Success) {
                    emit(Resource.Success(true))
                } else {
                    emit(Resource.Error(result.message.toString()))
                }
            }
        }.onFailure {
            it.printStackTrace()
            emit(Resource.Error(it.message.toString()))
        }
    }
}