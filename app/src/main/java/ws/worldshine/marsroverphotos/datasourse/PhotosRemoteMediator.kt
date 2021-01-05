package ws.worldshine.marsroverphotos.datasourse

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import ws.worldshine.marsroverphotos.database.Database
import ws.worldshine.marsroverphotos.entities.PhotosItem
import ws.worldshine.marsroverphotos.entities.RemoteKeys
import ws.worldshine.marsroverphotos.network.NasaApi
import java.io.IOException

@ExperimentalPagingApi
class PhotosRemoteMediator(
    private val database: Database,
    private val nasaApi: NasaApi
) : RemoteMediator<Int, PhotosItem>() {

    private companion object {
        private const val START_PAGE = 1
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PhotosItem>
    ): MediatorResult {

        try {
            val pageKeys = when (loadType) {
                LoadType.REFRESH -> null
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val remoteKeys = database.withTransaction {
                        database.remoteKeysDao()
                            .getRemoteKeyById(state.lastItemOrNull()?.id)
                    }
                    if (remoteKeys?.nextKey == null) {
                        return MediatorResult.Success(endOfPaginationReached = true)
                    }
                    remoteKeys.nextKey
                }
            }
            val page = pageKeys ?: START_PAGE
            return withContext(Dispatchers.IO) {
                database.withTransaction {  }
                val currentSol = nasaApi.getRoverManifest().body()?.photoManifest?.maxSol
                val freshList = currentSol?.let {
                    nasaApi.getPhotos(page = page, sol = it)
                        .body()?.photos.orEmpty()
                } as MutableList<PhotosItem>
                val endOfPaginationReached = freshList.isEmpty()

                // clear all tables in the database
                if (loadType == LoadType.REFRESH) {
                    database.remoteKeysDao().clearRemoteKeys()
                    database.nasaDao().clearPhotoItemTable()
                }
                val prevKey = if (page == START_PAGE) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                val keys = freshList.map {
                    RemoteKeys(id = it.id, prevKey = prevKey, nextKey = nextKey)
                }
                val badList = database.nasaDao().getAllBadPhotos()
                badList.map { badItem ->
                    freshList.removeAll {
                        it.id == badItem.photoId
                    }
                }
                database.nasaDao().insertPhotos(freshList)
                database.remoteKeysDao().insertAll(keys)

                MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
            }
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }

    }

}