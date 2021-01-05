package ws.worldshine.marsroverphotos.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bad_photos")
data class BadPhotoEntries(
    @PrimaryKey(autoGenerate = true) val uniqueId: Long,
    val photoId: Int
)