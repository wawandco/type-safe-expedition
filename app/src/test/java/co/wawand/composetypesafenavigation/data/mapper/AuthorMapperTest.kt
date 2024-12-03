package co.wawand.composetypesafenavigation.data.mapper

import co.wawand.composetypesafenavigation.data.local.database.entity.AlbumEntity
import co.wawand.composetypesafenavigation.data.local.database.entity.AuthorEntity
import co.wawand.composetypesafenavigation.data.local.database.entity.AuthorWithAlbumsAndPosts
import co.wawand.composetypesafenavigation.data.local.database.entity.PostEntity
import co.wawand.composetypesafenavigation.data.remote.api.entity.AuthorAPIEntity
import co.wawand.composetypesafenavigation.domain.model.Author
import org.junit.Assert.assertEquals
import org.junit.Test

class AuthorMapperTest {

    @Test
    fun `toDBEntity should correctly map API entity to DB entity`() {
        // Arrange
        val apiEntity = AuthorAPIEntity(
            id = 1,
            name = "John Doe",
            username = "johndoe",
            email = "john@example.com"
        )

        // Act
        val dbEntity = apiEntity.toDBEntity()

        // Assert
        assertEquals(1L, dbEntity.id)
        assertEquals("John Doe", dbEntity.name)
        assertEquals("johndoe", dbEntity.username)
        assertEquals("john@example.com", dbEntity.email)
    }

    @Test
    fun `toDomain should correctly map AuthorEntity to Author`() {
        // Arrange
        val authorEntity = AuthorEntity(
            id = 1,
            name = "John Doe",
            username = "johndoe",
            email = "john@example.com"
        )

        // Act
        val author = authorEntity.toDomain()

        // Assert
        assertEquals(1L, author.id)
        assertEquals("John Doe", author.name)
        assertEquals("johndoe", author.username)
        assertEquals("john@example.com", author.email)
    }

    @Test
    fun `toDomain list extension should map multiple AuthorEntities`() {
        // Arrange
        val authorEntities = listOf(
            AuthorEntity(
                id = 1,
                name = "John Doe",
                username = "johndoe",
                email = "john@example.com"
            ),
            AuthorEntity(
                id = 2,
                name = "Jane Smith",
                username = "janesmith",
                email = "jane@example.com"
            )
        )

        // Act
        val authors = authorEntities.toDomain()

        // Assert
        assertEquals(2, authors.size)
        assertEquals(1L, authors[0].id)
        assertEquals("John Doe", authors[0].name)
        assertEquals(2L, authors[1].id)
        assertEquals("Jane Smith", authors[1].name)
    }

    @Test
    fun `toDBEntity should correctly map Author to AuthorEntity`() {
        // Arrange
        val author = Author(
            id = 1,
            name = "John Doe",
            username = "johndoe",
            email = "john@example.com"
        )

        // Act
        val authorEntity = author.toDBEntity()

        // Assert
        assertEquals(1L, authorEntity.id)
        assertEquals("John Doe", authorEntity.name)
        assertEquals("johndoe", authorEntity.username)
        assertEquals("john@example.com", authorEntity.email)
    }

    @Test
    fun `toDBEntity list extension should map multiple Authors`() {
        // Arrange
        val authors = listOf(
            Author(
                id = 1,
                name = "John Doe",
                username = "johndoe",
                email = "john@example.com"
            ),
            Author(
                id = 2,
                name = "Jane Smith",
                username = "janesmith",
                email = "jane@example.com"
            )
        )

        // Act
        val authorEntities = authors.toDBEntity()

        // Assert
        assertEquals(2, authorEntities.size)
        assertEquals(1L, authorEntities[0].id)
        assertEquals("John Doe", authorEntities[0].name)
        assertEquals(2L, authorEntities[1].id)
        assertEquals("Jane Smith", authorEntities[1].name)
    }

    @Test
    fun `toDomain should correctly map AuthorWithAlbumsAndPosts to Author`() {
        // Arrange
        val authorEntity = AuthorEntity(
            id = 1,
            name = "John Doe",
            username = "johndoe",
            email = "john@example.com"
        )
        val albums = listOf(
            AlbumEntity(id = 1, title = "Album 1", ownerId = authorEntity.id),
            AlbumEntity(id = 2, title = "Album 2", ownerId = authorEntity.id)
        )
        val posts = listOf(
            PostEntity(id = 1, title = "Post 1", authorOwnerId = authorEntity.id),
            PostEntity(id = 2, title = "Post 2", authorOwnerId = authorEntity.id),
            PostEntity(id = 3, title = "Post 3", authorOwnerId = authorEntity.id)
        )
        val authorWithAlbumsAndPosts = AuthorWithAlbumsAndPosts(
            author = authorEntity,
            albums = albums,
            posts = posts
        )

        // Act
        val author = authorWithAlbumsAndPosts.toDomain()

        // Assert
        assertEquals(1L, author.id)
        assertEquals("John Doe", author.name)
        assertEquals("johndoe", author.username)
        assertEquals("john@example.com", author.email)
        assertEquals(2, author.albums)
        assertEquals(3, author.posts)
    }

    @Test
    fun `toDomain should handle null albums and posts`() {
        // Arrange
        val authorEntity = AuthorEntity(
            id = 1,
            name = "John Doe",
            username = "johndoe",
            email = "john@example.com"
        )
        val authorWithAlbumsAndPosts = AuthorWithAlbumsAndPosts(
            author = authorEntity,
            albums = null,
            posts = null
        )

        // Act
        val author = authorWithAlbumsAndPosts.toDomain()

        // Assert
        assertEquals(1L, author.id)
        assertEquals("John Doe", author.name)
        assertEquals("johndoe", author.username)
        assertEquals("john@example.com", author.email)
        assertEquals(0, author.albums)
        assertEquals(0, author.posts)
    }
}