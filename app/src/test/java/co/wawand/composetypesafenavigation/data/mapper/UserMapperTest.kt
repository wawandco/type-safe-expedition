package co.wawand.composetypesafenavigation.data.mapper

import co.wawand.composetypesafenavigation.data.local.database.entity.UserEntity
import co.wawand.composetypesafenavigation.domain.model.User
import org.junit.Assert.assertEquals
import org.junit.Test

class UserMapperTest {
    @Test
    fun `toDomain converts UserEntity to User correctly`() {
        // Arrange
        val userEntity = UserEntity(
            id = 1,
            name = "John Doe"
        )

        // Act
        val user = userEntity.toDomain()

        // Assert
        assertEquals(1L, user.id)
        assertEquals("John Doe", user.name)
    }

    @Test
    fun `toDBEntity converts User to UserEntity correctly`() {
        // Arrange
        val user = User(
            id = 1,
            name = "John Doe"
        )

        // Act
        val userEntity = user.toDBEntity()

        // Assert
        assertEquals(1L, userEntity.id)
        assertEquals("John Doe", userEntity.name)
    }

    @Test
    fun `mapper functions preserve all properties`() {
        // Arrange
        val originalUser = User(
            id = 42,
            name = "Alice Smith"
        )

        // Act
        val convertedEntity = originalUser.toDBEntity()
        val convertedBack = convertedEntity.toDomain()

        // Assert
        assertEquals(originalUser.id, convertedBack.id)
        assertEquals(originalUser.name, convertedBack.name)
    }
}