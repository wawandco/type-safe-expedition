package co.wawand.composetypesafenavigation.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.preferencesOf
import co.wawand.composetypesafenavigation.core.util.Resource
import co.wawand.composetypesafenavigation.data.local.datastore.PreferencesKeys
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class UserPreferencesRepositoryImplTest {

    @Mock
    private lateinit var mockDataStore: DataStore<Preferences>

    private lateinit var repository: UserPreferencesRepositoryImpl

    @Before
    fun setup() {
        repository = UserPreferencesRepositoryImpl(mockDataStore)
    }

    @Test
    fun `setUserId should return Success when setting user ID`() = runTest {
        // Arrange
        val userId = 456L

        // Act
        val result = repository.setUserId(userId)

        // Assert
        assert(result is Resource.Success)
        verify(mockDataStore).edit(any())
    }

    @Test
    fun `setUserId should return Error when exception occurs`() = runTest {
        // Arrange
        val userId = 123L
        whenever(mockDataStore.edit(any())).thenThrow(RuntimeException("Test error"))

        // Act
        val result = repository.setUserId(userId)

        // Assert
        assertTrue(result is Resource.Error)
        assertEquals("Test error", (result as Resource.Error).message)
    }

    @Test
    fun `getUserId should return existing user ID`() = runTest {
        // Arrange
        val userId = 456L
        val preferences = preferencesOf(PreferencesKeys.KEY_USER_ID to userId)
        whenever(mockDataStore.data).thenReturn(flowOf(preferences))

        // Act
        val result = repository.getUserId()

        // Assert
        assertTrue(result is Resource.Success)
        assertEquals(userId, (result as Resource.Success).data)
    }

    @Test
    fun `getUserId should return 0 when no user ID exists`() = runTest {
        // Arrange
        val preferences = emptyPreferences()
        whenever(mockDataStore.data).thenReturn(flowOf(preferences))

        // Act
        val result = repository.getUserId()

        // Assert
        assertTrue(result is Resource.Success)
        assertEquals(0L, (result as Resource.Success).data)
    }

    @Test
    fun `getUserId should return Error when exception occurs`() = runTest {
        // Arrange
        whenever(mockDataStore.data).thenThrow(RuntimeException("Test error"))

        // Act
        val result = repository.getUserId()

        // Assert
        assertTrue(result is Resource.Error)
        assertEquals("Test error", (result as Resource.Error).message)
    }
}