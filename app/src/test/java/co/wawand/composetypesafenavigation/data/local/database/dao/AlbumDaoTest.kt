package co.wawand.composetypesafenavigation.data.local.database.dao

import androidx.test.ext.junit.runners.AndroidJUnit4
import co.wawand.composetypesafenavigation.data.local.database.AppDatabase
import co.wawand.composetypesafenavigation.data.local.database.BaseDatabaseTest
import co.wawand.composetypesafenavigation.data.local.database.DbTestUtil
import co.wawand.composetypesafenavigation.data.local.database.entity.AlbumWithPhotosAndOwner
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AlbumDaoTest : BaseDatabaseTest<AppDatabase>() {
    private lateinit var albumDao: AlbumDao

    override fun getDatabaseClass(): Class<AppDatabase> = AppDatabase::class.java

    @Before
    override fun setup() {
        super.setup()
        albumDao = database.albumDAO()
    }

    @Test
    fun insertAndRetrieveAlbum() = runTest {
        val author = DbTestUtil.createAuthor(id = 1)
        database.authorDAO().insertAuthors(listOf(author))

        val album = DbTestUtil.createAlbum(id = 1, ownerId = author.id)

        albumDao.insertAlbums(listOf(album))

        val retrievedAlbum = albumDao.getAlbumById(1)
        assertEquals(AlbumWithPhotosAndOwner(album, emptyList(), author), retrievedAlbum)

    }
}