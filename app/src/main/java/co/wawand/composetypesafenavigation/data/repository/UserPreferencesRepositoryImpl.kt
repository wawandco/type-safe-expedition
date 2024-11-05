package co.wawand.composetypesafenavigation.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import co.wawand.composetypesafenavigation.core.Constant.GENERIC_ERROR
import co.wawand.composetypesafenavigation.core.util.Resource
import co.wawand.composetypesafenavigation.data.local.datastore.PreferencesKeys.KEY_USER_ID
import co.wawand.composetypesafenavigation.domain.repository.UserPreferencesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserPreferencesRepositoryImpl @Inject constructor(
    private val userDataStorePreferences: DataStore<Preferences>
) : UserPreferencesRepository {

    override suspend fun setUserId(userId: Long): Flow<Resource<Boolean>> = flow {
        emit(Resource.Loading())

        runCatching {
            userDataStorePreferences.edit { prefs ->
                prefs[KEY_USER_ID] = userId
            }
        }.onSuccess {
            emit(Resource.Success(true))
        }.onFailure {
            it.printStackTrace()
            emit(Resource.Error(it.message ?: GENERIC_ERROR))
        }
    }

    override suspend fun getUserId(): Flow<Resource<Long>> = flow {
        emit(Resource.Loading())

        runCatching {
            userDataStorePreferences.data
        }.onSuccess {
            val flow = it.map { prefs ->
                prefs[KEY_USER_ID]
            }.firstOrNull()
            val value = flow ?: 0L
            emit(Resource.Success(value))
        }.onFailure {
            it.printStackTrace()
            emit(Resource.Error(it.message ?: GENERIC_ERROR))
        }
    }
}