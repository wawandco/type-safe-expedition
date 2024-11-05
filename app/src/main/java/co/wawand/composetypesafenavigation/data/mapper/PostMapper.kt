package co.wawand.composetypesafenavigation.data.mapper

import co.wawand.composetypesafenavigation.data.local.database.entity.PostAndAuthor
import co.wawand.composetypesafenavigation.data.local.database.entity.PostEntity
import co.wawand.composetypesafenavigation.data.remote.api.entity.PostAPIEntity
import co.wawand.composetypesafenavigation.domain.model.Post

fun PostAPIEntity.toDBEntity(): PostEntity = PostEntity(
    id = id,
    title = title,
    body = body,
    authorOwnerId = userId
)

fun PostAndAuthor.toDomain(): Post {
    val postEntity = this.post
    val authorEntity = this.author

    return Post(
        id = postEntity.id,
        title = postEntity.title,
        body = postEntity.body,
        author = authorEntity?.toDomain()
    )
}

fun List<PostAndAuthor>.toDomain(): List<Post> = map { it.toDomain() }