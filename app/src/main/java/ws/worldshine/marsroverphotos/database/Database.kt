package ws.worldshine.marsroverphotos.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ws.worldshine.marsroverphotos.converters.CameraConverter
import ws.worldshine.marsroverphotos.converters.RoverConverter
import ws.worldshine.marsroverphotos.entities.BadPhotoEntries
import ws.worldshine.marsroverphotos.entities.PhotosItem
import ws.worldshine.marsroverphotos.entities.RemoteKeys

@Database(
    entities = [
        PhotosItem::class,
        RemoteKeys::class,
        BadPhotoEntries::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(CameraConverter::class, RoverConverter::class)
abstract class Database : RoomDatabase() {
    abstract fun nasaDao(): NasaDao
    abstract fun remoteKeysDao(): RemoteKeysDao
}