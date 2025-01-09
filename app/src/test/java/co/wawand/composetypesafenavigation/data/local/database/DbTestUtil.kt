package co.wawand.composetypesafenavigation.data.local.database

import co.wawand.composetypesafenavigation.data.local.database.entity.AlbumEntity
import co.wawand.composetypesafenavigation.data.local.database.entity.AuthorEntity
import co.wawand.composetypesafenavigation.data.local.database.entity.PhotoEntity
import co.wawand.composetypesafenavigation.data.local.database.entity.PhotoType
import co.wawand.composetypesafenavigation.data.local.database.entity.PostEntity

object DbTestUtil {
    fun createAuthor(
        id: Long = 0,
        name: String = "Test Author",
        username: String = "testuser",
        email: String = "test@example.com"
    ) = AuthorEntity(
        id = id,
        name = name,
        username = username,
        email = email
    )

    fun createAlbum(
        id: Long = 0,
        title: String = "Test Album",
        ownerId: Long = 0
    ) = AlbumEntity(
        id = id,
        title = title,
        ownerId = ownerId
    )

    fun createPhoto(
        id: Long = 0,
        title: String = "Test Photo",
        url: String = "https://example.com/photo.jpg",
        thumbnailUrl: String = "https://example.com/thumbnail.jpg",
        albumId: Long = 0
    ) = PhotoEntity(
        id = id,
        title = title,
        type = PhotoType.REMOTE,
        url = url,
        thumbnailUrl = thumbnailUrl,
        albumId = albumId
    )

    fun createPost(
        id: Long = 0,
        title: String = "Test Post",
        body: String = "Test Body",
        authorOwnerId: Long = 0
    ) = PostEntity(
        id = id,
        title = title,
        body = body,
        authorOwnerId = authorOwnerId
    )
}