package co.wawand.composetypesafenavigation.data.mapper

import co.wawand.composetypesafenavigation.data.local.database.entity.UserEntity
import co.wawand.composetypesafenavigation.domain.model.User

fun UserEntity.toDomain(): User = User(
    id = id,
    name = name
)

fun User.toDBEntity(): UserEntity = UserEntity(
    id = id,
    name = name
)