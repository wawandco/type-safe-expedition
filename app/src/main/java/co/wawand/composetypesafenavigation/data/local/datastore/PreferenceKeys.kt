package co.wawand.composetypesafenavigation.data.local.datastore

import androidx.datastore.preferences.core.longPreferencesKey
import co.wawand.composetypesafenavigation.core.Constant

object PreferencesKeys {
    val KEY_USER_ID = longPreferencesKey(name = Constant.USER_ID)
    val KEY_LOG_TIMESTAMP = longPreferencesKey(name = Constant.LOG_TIMESTAMP)
}