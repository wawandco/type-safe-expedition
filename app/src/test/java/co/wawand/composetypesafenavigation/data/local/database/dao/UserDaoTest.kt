package co.wawand.composetypesafenavigation.data.local.database.dao

import androidx.test.ext.junit.runners.AndroidJUnit4
import co.wawand.composetypesafenavigation.data.local.database.AppDatabase
import co.wawand.composetypesafenavigation.data.local.database.BaseDatabaseTest
import co.wawand.composetypesafenavigation.data.mapper.toDBEntity
import co.wawand.composetypesafenavigation.domain.model.User
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before

import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UserDaoTest : BaseDatabaseTest<AppDatabase>() {
    private lateinit var userDao: UserDao

    override fun getDatabaseClass(): Class<AppDatabase> = AppDatabase::class.java

    @Before
    override fun setup() {
        super.setup()
        userDao = database.userDAO()
    }

    @Test
    fun insertAndRetrieveUser() = runTest {
        val user = User(id = 1, name = "Test User").toDBEntity()
        userDao.upsertUser(user)

        val retrievedUser = userDao.getUserById(1)
        assertEquals(user, retrievedUser)
    }
}