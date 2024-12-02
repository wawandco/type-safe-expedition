package co.wawand.composetypesafenavigation.data.local.database.dao

import androidx.test.ext.junit.runners.AndroidJUnit4
import co.wawand.composetypesafenavigation.data.local.database.AppDatabase
import co.wawand.composetypesafenavigation.data.local.database.BaseDatabaseTest
import co.wawand.composetypesafenavigation.data.local.database.DbTestUtil
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AuthorDaoTest : BaseDatabaseTest<AppDatabase>() {
    private lateinit var authorDao: AuthorDao

    override fun getDatabaseClass(): Class<AppDatabase> = AppDatabase::class.java

    @Before
    override fun setup() {
        super.setup()
        authorDao = database.authorDAO()
    }

    @Test
    fun insertAndRetrieveAuthor_withNoRelatedEntities() = runTest {
        // Arrange
        val author = DbTestUtil.createAuthor(id = 1)

        // Act
        authorDao.insertAuthors(listOf(author))
        val retrievedAuthors = authorDao.getAuthors()

        // Assert
        assertEquals(1, retrievedAuthors.size)
        val retrievedAuthor = retrievedAuthors.first()
        assertEquals(author, retrievedAuthor.author)
        assertTrue(retrievedAuthor.albums.isNullOrEmpty())
        assertTrue(retrievedAuthor.posts.isNullOrEmpty())
    }

    @Test
    fun insertAndRetrieveAuthor_withRelatedEntities() = runTest {
        // Arrange
        val author = DbTestUtil.createAuthor(id = 1)

        val album = DbTestUtil.createAlbum(id = 1, ownerId = author.id)

        val post = DbTestUtil.createPost(id = 1, authorOwnerId = author.id)

        // Act
        authorDao.insertAuthors(listOf(author))
        // You'll need to insert albums and posts in their respective DAOs
        database.albumDAO().insertAlbums(listOf(album))
        database.postDAO().insertPosts(listOf(post))

        val retrievedAuthors = authorDao.getAuthors()

        // Assert
        assertEquals(1, retrievedAuthors.size)
        val retrievedAuthor = retrievedAuthors.first()
        assertEquals(author, retrievedAuthor.author)
        assertEquals(1, retrievedAuthor.albums?.size)
        assertEquals(album, retrievedAuthor.albums?.first())
        assertEquals(1, retrievedAuthor.posts?.size)
        assertEquals(post, retrievedAuthor.posts?.first())
    }

    @Test
    fun insertMultipleAuthors_retrieveAll() = runTest {
        // Arrange
        val authors = listOf(
            DbTestUtil.createAuthor(
                1,
                name = "Test User 1",
                username = "testuser1",
                email = "john1.mclean@examplepetstore.com"
            ),
            DbTestUtil.createAuthor(
                2,
                name = "Test User 2",
                username = "testuser2",
                email = "john2.mclean@examplepetstore.com"
            )
        )

        // Act
        authorDao.insertAuthors(authors)
        val retrievedAuthors = authorDao.getAuthors()

        // Assert
        assertEquals(authors.size, retrievedAuthors.size)
        retrievedAuthors.forEachIndexed { index, retrievedAuthor ->
            assertEquals(authors[index], retrievedAuthor.author)
        }
    }
}