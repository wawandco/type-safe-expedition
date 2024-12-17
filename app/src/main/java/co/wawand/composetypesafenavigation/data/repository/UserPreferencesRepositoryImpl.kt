package co.wawand.composetypesafenavigation.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import co.wawand.composetypesafenavigation.core.Constant.GENERIC_ERROR
import co.wawand.composetypesafenavigation.core.util.Resource
import co.wawand.composetypesafenavigation.data.local.datastore.PreferencesKeys.KEY_USER_ID
import co.wawand.composetypesafenavigation.domain.repository.UserPreferencesRepository
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserPreferencesRepositoryImpl @Inject constructor(
    private val userDataStorePreferences: DataStore<Preferences>
) : UserPreferencesRepository {

    override suspend fun setUserId(userId: Long): Resource<Boolean> {
        return try {
            userDataStorePreferences.edit { prefs ->
                prefs[KEY_USER_ID] = userId
            }
            Resource.Success(true)
        } catch (e: Exception) {
            Resource.Error(e.message ?: GENERIC_ERROR)
        }
    }

    override suspend fun getUserId(): Resource<Long> {
        return try {
            val userId = userDataStorePreferences.data.map { prefs ->
                prefs[KEY_USER_ID]
            }.firstOrNull()
            Resource.Success(userId ?: 0L)
        } catch (e: Exception) {
            Resource.Error(e.message ?: GENERIC_ERROR)
        }
    }
}