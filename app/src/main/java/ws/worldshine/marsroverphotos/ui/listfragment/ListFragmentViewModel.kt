package ws.worldshine.marsroverphotos.ui.listfragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.room.withTransaction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ws.worldshine.marsroverphotos.database.Database
import ws.worldshine.marsroverphotos.entities.BadPhotoEntries
import ws.worldshine.marsroverphotos.entities.PhotosItem
import ws.worldshine.marsroverphotos.network.NasaApi
import ws.worldshine.marsroverphotos.repositories.PhotoRepository
import javax.inject.Inject

@ExperimentalPagingApi
class ListFragmentViewModel @Inject constructor(
    private val database: Database,
    nasaApi: NasaApi
) : ViewModel() {
    private val repository = PhotoRepository(database, nasaApi)

    fun getData(): LiveData<PagingData<PhotosItem>> {
        return repository.getResultAsLiveData()
            .cachedIn(viewModelScope)
    }

    suspend fun getImagesList(): LiveData<List<String>> {
        return database.withTransaction {
            database.nasaDao().getAllImageSrc()
        }
    }

    fun deleteItem(photosItem: PhotosItem) {
        viewModelScope.launch(Dispatchers.IO) {
            database.nasaDao().insertBadPhotos(BadPhotoEntries(uniqueId = 0, photoId = photosItem.id))
            database.nasaDao().deletePhotosItem(photosItem.id)
        }
    }

}