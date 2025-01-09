package co.wawand.composetypesafenavigation.presentation.utils.mockdata

import co.wawand.composetypesafenavigation.domain.model.Album
import co.wawand.composetypesafenavigation.domain.model.AlbumWithPhotos
import co.wawand.composetypesafenavigation.domain.model.RemotePhoto

fun generateStaticAlbums(): List<Album> {
    val authors = generateStaticAuthors()
    return listOf(
        Album(
            id = 1,
            title = "Amazing Cool Stuff",
            photos = 3,
            owner = authors[0]
        ),
        Album(
            id = 2,
            title = "Nothing compare 2 U",
            photos = 12,
            owner = authors[1]
        ),
        Album(
            id = 3,
            title = "Glorazzy Love",
            photos = 341,
            owner = authors[2]
        )
    )
}

fun generateStaticAlbumWithPhotos(): AlbumWithPhotos = AlbumWithPhotos(
    id = 1,
    title = "Amazing Cool Stuff",
    photos = generateStaticPhotos(),
    owner = generateStaticAuthors()[0]
)

fun generateStaticPhotos(): List<RemotePhoto> {
    val albums = generateStaticAlbums()

    return listOf(
        RemotePhoto(
            id = 1,
            title = "Photo 1",
            thumbnailUrl = "https://picsum.photos/seed/${(0..100000).random()}/256/256",
            url = "https://picsum.photos/2400/1600?id=1",
            album = albums[0].title
        ),
        RemotePhoto(
            id = 2,
            title = "Photo 2",
            thumbnailUrl = "https://picsum.photos/seed/${(0..100000).random()}/256/256",
            url = "https://picsum.photos/2400/1600?id=2",
            album = albums[0].title
        ),
        RemotePhoto(
            id = 3,
            title = "Photo 3",
            thumbnailUrl = "https://picsum.photos/seed/${(0..100000).random()}/256/256",
            url = "https://picsum.photos/2400/1600?id=3",
            album = albums[0].title
        ),
        RemotePhoto(
            id = 4,
            title = "Photo 4",
            thumbnailUrl = "https://picsum.photos/seed/${(0..100000).random()}/256/256",
            url = "https://picsum.photos/2400/1600?id=4",
            album = albums[1].title
        ),
        RemotePhoto(
            id = 5,
            title = "Photo 5",
            thumbnailUrl = "https://picsum.photos/seed/${(0..100000).random()}/256/256",
            url = "https://picsum.photos/2400/1600?id=5",
            album = albums[1].title
        ),
        RemotePhoto(
            id = 6,
            title = "Photo 6",
            thumbnailUrl = "https://picsum.photos/seed/${(0..100000).random()}/256/256",
            url = "https://picsum.photos/2400/1600?id=6",
            album = albums[1].title
        ),
        RemotePhoto(
            id = 7,
            title = "Photo 7",
            thumbnailUrl = "https://picsum.photos/seed/${(0..100000).random()}/256/256",
            url = "https://picsum.photos/2400/1600?id=7",
            album = albums[2].title
        ),
        RemotePhoto(
            id = 8,
            title = "Photo 8",
            thumbnailUrl = "https://picsum.photos/seed/${(0..100000).random()}/256/256",
            url = "https://picsum.photos/2400/1600?id=8",
            album = albums[2].title
        ),
        RemotePhoto(
            id = 9,
            title = "Photo 8",
            thumbnailUrl = "https://picsum.photos/seed/${(0..100000).random()}/256/256",
            url = "https://picsum.photos/2400/1600?id=8",
            album = albums[2].title
        ),
    )
}