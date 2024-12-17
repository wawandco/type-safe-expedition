package co.wawand.composetypesafenavigation.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import co.wawand.composetypesafenavigation.core.Constant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

val Context.userDataStore: DataStore<Preferences> by preferencesDataStore(
    name = Constant.USER_PREFERENCES
)

@Module
@InstallIn(SingletonComponent::class)
object UserPreferencesModule {

    @Provides
    @Singleton
    fun provideUserDataStorePreferences(
        @ApplicationContext applicationContext: Context
    ): DataStore<Preferences> = applicationContext.userDataStore
}