package co.wawand.composetypesafenavigation.data.mapper

import co.wawand.composetypesafenavigation.data.local.database.entity.AuthorEntity
import co.wawand.composetypesafenavigation.data.local.database.entity.AuthorWithAlbumsAndPosts
import co.wawand.composetypesafenavigation.data.remote.api.entity.AuthorAPIEntity
import co.wawand.composetypesafenavigation.domain.model.Author

fun AuthorAPIEntity.toDBEntity(): AuthorEntity = AuthorEntity(
    id = id,
    name = name,
    username = username,
    email = email
)

fun AuthorEntity.toDomain(): Author = Author(
    id = id,
    name = name,
    username = username,
    email = email
)

fun List<AuthorEntity>.toDomain(): List<Author> = map { it.toDomain() }

fun Author.toDBEntity(): AuthorEntity = AuthorEntity(
    id = id,
    name = name,
    username = username,
    email = email
)

fun List<Author>.toDBEntity(): List<AuthorEntity> = map { it.toDBEntity() }

fun AuthorWithAlbumsAndPosts.toDomain(): Author = Author(
    id = author.id,
    name = author.name,
    username = author.username,
    email = author.email,
    albums = albums?.size ?: 0,
    posts = posts?.size ?: 0,
)