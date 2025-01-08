package co.wawand.composetypesafenavigation.data.mapper

import co.wawand.composetypesafenavigation.data.local.database.entity.AlbumEntity
import co.wawand.composetypesafenavigation.data.local.database.entity.PhotoEntity
import co.wawand.composetypesafenavigation.data.local.database.entity.PhotoWithAlbum
import co.wawand.composetypesafenavigation.data.remote.api.entity.PhotoAPIEntity
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertNotNull
import org.junit.Test

class PhotoMapperTest {

    @Test
    fun `toDBEntity generates unique thumbnail and url with random seed`() {
        // Arrange
        val apiEntity = PhotoAPIEntity(
            id = 1,
            title = "Test Photo",
            url = "https://example.com/full",
            thumbnailUrl = "https://example.com/thumb",
            albumId = 10
        )

        // Act
        val dbEntity1 = apiEntity.toDBEntity()
        val dbEntity2 = apiEntity.toDBEntity()

        // Assert
        assertNotNull(dbEntity1.thumbnailUrl)
        assertNotNull(dbEntity1.url)
        assertNotEquals(dbEntity1.thumbnailUrl, dbEntity2.thumbnailUrl)
        assertNotEquals(dbEntity1.url, dbEntity2.url)
    }

    @Test
    fun `toDomain converts PhotoEntity to Photo correctly`() {
        // Arrange
        val photoEntity = PhotoEntity(
            id = 1,
            title = "Test Photo",
            thumbnailUrl = "https://example.com/thumb",
            url = "https://example.com/full",
            albumId = 10
        )

        // Act
        val photo = photoEntity.toDomain()

        // Assert
        assertEquals(1, photo.id)
        assertEquals("Test Photo", photo.title)
        assertEquals("https://example.com/thumb", photo.thumbnailUrl)
        assertEquals("https://example.com/full", photo.url)
        assertEquals("10", photo.album)
    }

    @Test
    fun `toDomain converts PhotoWithAlbum to Photo correctly`() {
        // Arrange
        val photoEntity = PhotoEntity(
            id = 1,
            title = "Test Photo",
            thumbnailUrl = "https://example.com/thumb",
            url = "https://example.com/full",
            albumId = 10
        )
        val albumEntity = AlbumEntity(
            id = 10,
            title = "Test Album",
            ownerId = 1
        )
        val photoWithAlbum = PhotoWithAlbum(
            photoEntity = photoEntity,
            albumEntity = albumEntity
        )

        // Act
        val photo = photoWithAlbum.toDomain()

        // Assert
        assertEquals(1, photo.id)
        assertEquals("Test Photo", photo.title)
        assertEquals("https://example.com/thumb", photo.thumbnailUrl)
        assertEquals("https://example.com/full", photo.url)
        assertEquals("Test Album", photo.album)
    }
}