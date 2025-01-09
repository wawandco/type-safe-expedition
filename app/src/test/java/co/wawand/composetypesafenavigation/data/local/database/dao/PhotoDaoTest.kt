package co.wawand.composetypesafenavigation.data.local.database.dao

import androidx.test.ext.junit.runners.AndroidJUnit4
import co.wawand.composetypesafenavigation.data.local.database.AppDatabase
import co.wawand.composetypesafenavigation.data.local.database.BaseDatabaseTest
import co.wawand.composetypesafenavigation.data.local.database.DbTestUtil
import co.wawand.composetypesafenavigation.data.local.database.entity.PhotoWithAlbum
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PhotoDaoTest : BaseDatabaseTest<AppDatabase>() {
    private lateinit var photoDao: PhotoDao

    override fun getDatabaseClass(): Class<AppDatabase> = AppDatabase::class.java

    @Before
    override fun setup() {
        super.setup()
        photoDao = database.photoDAO()
    }

    @Test
    fun insertAndRetrieveAlbum() = runTest {
        val author = DbTestUtil.createAuthor(id = 1)
        database.authorDAO().insertAuthors(listOf(author))

        val album = DbTestUtil.createAlbum(id = 1, ownerId = author.id)
        database.albumDAO().insertAlbums(listOf(album))

        val photo = DbTestUtil.createPhoto(id = 1, albumId = album.id)
        photoDao.insertPhotos(listOf(photo))

        val photos = photoDao.getPhotos()
        assertEquals(1, photos.size)

        val photoWithAlbumById = photoDao.getPhotoWithAlbumById(1)
        assertEquals(PhotoWithAlbum(photo, album), photoWithAlbumById)
        assertEquals(1L, photoWithAlbumById.photoEntity.id)
        assertEquals(album.id, photoWithAlbumById.albumEntity?.id)
    }
}