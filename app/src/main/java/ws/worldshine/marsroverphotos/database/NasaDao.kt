package ws.worldshine.marsroverphotos.database

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ws.worldshine.marsroverphotos.entities.BadPhotoEntries
import ws.worldshine.marsroverphotos.entities.PhotosItem

@Dao
interface NasaDao {

    @Query("SELECT * FROM photo_item")
    fun getAllPhotos(): PagingSource<Int, PhotosItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPhotos(photos: List<PhotosItem>)

    @Query("DELETE FROM photo_item")
    suspend fun clearPhotoItemTable()

    @Query("DELETE FROM photo_item WHERE id =:id")
    suspend fun deletePhotosItem(id: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBadPhotos(badPhotoEntries: BadPhotoEntries)

    @Query("SELECT * FROM bad_photos")
    suspend fun getAllBadPhotos(): List<BadPhotoEntries>

    @Query("SELECT imgSrc FROM photo_item")
    fun getAllImageSrc(): LiveData<List<String>>

    @Query("SELECT sol FROM photo_item WHERE sol = (SELECT MAX(sol) FROM photo_item) LIMIT 1")
    suspend fun getLastSol(): Int?
}