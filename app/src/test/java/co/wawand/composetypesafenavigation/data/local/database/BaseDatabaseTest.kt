package co.wawand.composetypesafenavigation.data.local.database

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.test.core.app.ApplicationProvider
import org.junit.After
import org.junit.Before
import org.junit.Rule
import java.util.concurrent.Executors

abstract class BaseDatabaseTest<T : RoomDatabase> {
    protected lateinit var database: T

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    open fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            getDatabaseClass()
        )
            .allowMainThreadQueries()
            .setTransactionExecutor(Executors.newSingleThreadExecutor()).build()
    }

    @After
    open fun tearDown() {
        database.close()
    }

    // Implement this in your specific test classes
    abstract fun getDatabaseClass(): Class<T>
}