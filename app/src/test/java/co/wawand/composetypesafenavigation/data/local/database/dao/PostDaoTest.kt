package co.wawand.composetypesafenavigation.data.local.database.dao

import androidx.test.ext.junit.runners.AndroidJUnit4
import co.wawand.composetypesafenavigation.data.local.database.AppDatabase
import co.wawand.composetypesafenavigation.data.local.database.BaseDatabaseTest
import co.wawand.composetypesafenavigation.data.local.database.DbTestUtil
import co.wawand.composetypesafenavigation.data.local.database.entity.PostAndAuthor
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PostDaoTest : BaseDatabaseTest<AppDatabase>() {
    private lateinit var postDao: PostDao

    override fun getDatabaseClass(): Class<AppDatabase> = AppDatabase::class.java

    @Before
    override fun setup() {
        super.setup()
        postDao = database.postDAO()
    }

    @Test
    fun insertAndRetrievePost() = runTest {
        val author = DbTestUtil.createAuthor(id = 1)
        database.authorDAO().insertAuthors(listOf(author))

        val post = DbTestUtil.createPost(id = 1, authorOwnerId = author.id)
        postDao.insertPosts(listOf(post))

        val retrievedPost = postDao.getPostById(1)
        assertEquals(PostAndAuthor(post, author), retrievedPost)

        val postsByAuthor = postDao.getPostsByAuthor(1)
        assertEquals(listOf(PostAndAuthor(post, author)), postsByAuthor)

        val posts = postDao.getPosts()
        assertEquals(listOf(PostAndAuthor(post, author)), posts)
    }
}