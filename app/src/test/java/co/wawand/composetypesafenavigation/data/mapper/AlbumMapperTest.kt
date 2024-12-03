package co.wawand.composetypesafenavigation.data.mapper

import co.wawand.composetypesafenavigation.data.local.database.entity.AlbumEntity
import co.wawand.composetypesafenavigation.data.local.database.entity.AlbumWithPhotosAndOwner
import co.wawand.composetypesafenavigation.data.local.database.entity.AuthorEntity
import co.wawand.composetypesafenavigation.data.local.database.entity.PhotoEntity
import co.wawand.composetypesafenavigation.data.remote.api.entity.AlbumAPIEntity
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test

class AlbumMapperTest {

    @Test
    fun `toDBEntity should correctly map API entity to DB entity`() {
        // Arrange
        val apiEntity = AlbumAPIEntity(
            id = 1,
            title = "Test Album",
            userId = 100
        )

        // Act
        val dbEntity = apiEntity.toDBEntity()

        // Assert
        assertEquals(1, dbEntity.id)
        assertEquals("Test Album", dbEntity.title)
        assertEquals(100, dbEntity.ownerId)
    }

    @Test
    fun `toDomain should correctly map AlbumWithPhotosAndOwner to Album`() {
        // Arrange
        val ownerEntity = AuthorEntity(
            id = 100,
            name = "Test Owner",
            username = "testuser",
            email = "test@example.com"
        )
        val albumEntity = AlbumEntity(
            id = 1,
            title = "Test Album",
            ownerId = ownerEntity.id
        )
        val photoEntityList = listOf(
            PhotoEntity(
                id = 1,
                title = "photo1",
                thumbnailUrl = "http://example.com/photo1",
                url = "http://example.com/photo1",
                albumId = albumEntity.id
            ),
            PhotoEntity(
                id = 2,
                title = "photo2",
                thumbnailUrl = "http://example.com/photo2",
                url = "http://example.com/photo2",
                albumId = albumEntity.id
            )
        )
        val albumWithPhotosAndOwner = AlbumWithPhotosAndOwner(
            albumEntity = albumEntity,
            ownerEntity = ownerEntity,
            photoEntityList = photoEntityList
        )

        // Act
        val album = albumWithPhotosAndOwner.toDomain()

        // Assert
        assertEquals(1, album.id)
        assertEquals("Test Album", album.title)
        assertEquals(2, album.photos)
        assertNotNull(album.owner)
        assertEquals(100L, album.owner?.id)
        assertEquals("Test Owner", album.owner?.name)
    }

    @Test
    fun `toDomain list extension should map multiple AlbumWithPhotosAndOwner`() {
        // Arrange
        val ownerEntity1 = AuthorEntity(
            id = 100,
            name = "Test Owner 1",
            username = "testuser1",
            email = "test1@example.com"
        )
        val albumEntity1 = AlbumEntity(
            id = 1,
            title = "Test Album 1",
            ownerId = ownerEntity1.id
        )
        val photoEntityList1 = listOf(
            PhotoEntity(
                id = 1,
                title = "photo1",
                thumbnailUrl = "http://example.com/photo1",
                url = "http://example.com/photo1",
                albumId = albumEntity1.id
            ),
            PhotoEntity(
                id = 2,
                title = "photo2",
                thumbnailUrl = "http://example.com/photo2",
                url = "http://example.com/photo2",
                albumId = albumEntity1.id
            )
        )
        val albumWithPhotosAndOwner1 = AlbumWithPhotosAndOwner(
            albumEntity = albumEntity1,
            ownerEntity = ownerEntity1,
            photoEntityList = photoEntityList1
        )

        val ownerEntity2 = AuthorEntity(
            id = 101,
            name = "Test Owner 2",
            username = "testuser2",
            email = "test2@example.com"
        )
        val albumEntity2 = AlbumEntity(
            id = 2,
            title = "Test Album 2",
            ownerId = ownerEntity2.id
        )
        val photoEntityList2 = listOf(
            PhotoEntity(
                id = 3,
                title = "photo3",
                thumbnailUrl = "http://example.com/photo3",
                url = "http://example.com/photo3",
                albumId = albumEntity2.id
            )
        )
        val albumWithPhotosAndOwner2 = AlbumWithPhotosAndOwner(
            albumEntity = albumEntity2,
            ownerEntity = ownerEntity2,
            photoEntityList = photoEntityList2
        )

        val albumList = listOf(albumWithPhotosAndOwner1, albumWithPhotosAndOwner2)

        // Act
        val domainAlbums = albumList.toDomain()

        // Assert
        assertEquals(2, domainAlbums.size)
        assertEquals(1, domainAlbums[0].id)
        assertEquals(2, domainAlbums[1].id)
        assertEquals(2, domainAlbums[0].photos)
        assertEquals(1, domainAlbums[1].photos)
    }

    @Test
    fun `toAlbumWithPhotosDomain should correctly map to AlbumWithPhotos`() {
        // Arrange
        val ownerEntity = AuthorEntity(
            id = 100,
            name = "Test Owner",
            username = "testuser",
            email = "test@example.com"
        )
        val albumEntity = AlbumEntity(
            id = 1,
            title = "Test Album",
            ownerId = ownerEntity.id
        )
        val photoEntityList = listOf(
            PhotoEntity(
                id = 1,
                albumId = albumEntity.id,
                title = "Photo 1",
                thumbnailUrl = "http://example.com/photo1",
                url = "http://example.com/photo1"
            ),
            PhotoEntity(
                id = 2,
                albumId = albumEntity.id,
                title = "Photo 2",
                thumbnailUrl = "http://example.com/photo2",
                url = "http://example.com/photo2"
            )
        )
        val albumWithPhotosAndOwner = AlbumWithPhotosAndOwner(
            albumEntity = albumEntity,
            ownerEntity = ownerEntity,
            photoEntityList = photoEntityList
        )

        // Act
        val albumWithPhotos = albumWithPhotosAndOwner.toAlbumWithPhotosDomain()

        // Assert
        assertEquals(1, albumWithPhotos.id)
        assertEquals("Test Album", albumWithPhotos.title)
        assertEquals(2, albumWithPhotos.photos.size)
        assertNotNull(albumWithPhotos.owner)
        assertEquals(100L, albumWithPhotos.owner?.id)
        assertEquals("Test Owner", albumWithPhotos.owner?.name)
    }
}