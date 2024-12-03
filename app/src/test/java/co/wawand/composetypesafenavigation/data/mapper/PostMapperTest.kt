package co.wawand.composetypesafenavigation.data.mapper

import co.wawand.composetypesafenavigation.data.local.database.entity.AuthorEntity
import co.wawand.composetypesafenavigation.data.local.database.entity.PostAndAuthor
import co.wawand.composetypesafenavigation.data.local.database.entity.PostEntity
import co.wawand.composetypesafenavigation.data.remote.api.entity.PostAPIEntity
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Test

class PostMapperTest {

    @Test
    fun `toDBEntity converts PostAPIEntity to PostEntity correctly`() {
        // Arrange
        val postAPIEntity = PostAPIEntity(
            id = 1,
            title = "Test Post",
            body = "Post body text",
            userId = 10
        )

        // Act
        val postEntity = postAPIEntity.toDBEntity()

        // Assert
        assertEquals(1L, postEntity.id)
        assertEquals("Test Post", postEntity.title)
        assertEquals("Post body text", postEntity.body)
        assertEquals(10L, postEntity.authorOwnerId)
    }

    @Test
    fun `toDomain converts PostAndAuthor to Post correctly with author`() {
        // Arrange
        val postEntity = PostEntity(
            id = 1,
            title = "Test Post",
            body = "Post body text",
            authorOwnerId = 10
        )
        val authorEntity = AuthorEntity(
            id = 10,
            name = "John Doe",
            username = "johndoe",
            email = "john@example.com"
        )
        val postAndAuthor = PostAndAuthor(
            post = postEntity,
            author = authorEntity
        )

        // Act
        val post = postAndAuthor.toDomain()

        // Assert
        assertEquals(1L, post.id)
        assertEquals("Test Post", post.title)
        assertEquals("Post body text", post.body)
        assertNotNull(post.author)
        assertEquals("John Doe", post.author?.name)
        assertEquals("john@example.com", post.author?.email)
    }

    @Test
    fun `toDomain converts PostAndAuthor to Post correctly without author`() {
        // Arrange
        val postEntity = PostEntity(
            id = 1,
            title = "Test Post",
            body = "Post body text",
            authorOwnerId = 10
        )
        val postAndAuthor = PostAndAuthor(
            post = postEntity,
            author = null
        )

        // Act
        val post = postAndAuthor.toDomain()

        // Assert
        assertEquals(1L, post.id)
        assertEquals("Test Post", post.title)
        assertEquals("Post body text", post.body)
        assertNull(post.author)
    }

    @Test
    fun `toDomain converts list of PostAndAuthor to list of Post`() {
        // Arrange
        val postEntity1 = PostEntity(
            id = 1,
            title = "Test Post 1",
            body = "Post body 1",
            authorOwnerId = 10
        )
        val postEntity2 = PostEntity(
            id = 2,
            title = "Test Post 2",
            body = "Post body 2",
            authorOwnerId = 11
        )
        val authorEntity1 = AuthorEntity(
            id = 10,
            name = "John Doe",
            username = "johndoe",
            email = "john@example.com"
        )
        val authorEntity2 = AuthorEntity(
            id = 11,
            name = "Jane Smith",
            username = "janesmith",
            email = "jane@example.com"
        )
        val postAndAuthorList = listOf(
            PostAndAuthor(post = postEntity1, author = authorEntity1),
            PostAndAuthor(post = postEntity2, author = authorEntity2)
        )

        // Act
        val postList = postAndAuthorList.toDomain()

        // Assert
        assertEquals(2, postList.size)
        assertEquals("Test Post 1", postList[0].title)
        assertEquals("Test Post 2", postList[1].title)
        assertEquals("John Doe", postList[0].author?.name)
        assertEquals("Jane Smith", postList[1].author?.name)
    }
}