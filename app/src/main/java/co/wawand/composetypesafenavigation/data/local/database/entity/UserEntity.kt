package co.wawand.composetypesafenavigation.data.local.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val name: String,

    @ColumnInfo("createdAt") var createdAt: Long = System.currentTimeMillis(),
    @ColumnInfo("updatedAt") var updatedAt: Long = System.currentTimeMillis(),
)