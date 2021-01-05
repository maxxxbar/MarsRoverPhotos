package ws.worldshine.marsroverphotos.repositories

import androidx.lifecycle.LiveData
import androidx.paging.*
import ws.worldshine.marsroverphotos.database.Database
import ws.worldshine.marsroverphotos.datasourse.PhotosRemoteMediator
import ws.worldshine.marsroverphotos.entities.PhotosItem
import ws.worldshine.marsroverphotos.network.NasaApi

@ExperimentalPagingApi
class PhotoRepository(
    database: Database,
    nasaApi: NasaApi
) {
    companion object {
        const val NETWORK_PAGE_SIZE = 25
    }
    private val config =
        PagingConfig(pageSize = NETWORK_PAGE_SIZE, enablePlaceholders = true, initialLoadSize = 50)
    private val photoRemoteMediator = PhotosRemoteMediator(database, nasaApi)
    private val pagingSourceFactory = { database.nasaDao().getAllPhotos() }

    fun getResultAsLiveData(): LiveData<PagingData<PhotosItem>> {
        return Pager(
            config = config,
            remoteMediator = photoRemoteMediator,
            pagingSourceFactory = pagingSourceFactory
        ).liveData
    }
}