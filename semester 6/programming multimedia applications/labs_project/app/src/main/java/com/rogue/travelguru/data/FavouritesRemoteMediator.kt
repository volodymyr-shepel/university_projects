package com.rogue.travelguru.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.rogue.travelguru.data.local.SightDatabase
import com.rogue.travelguru.data.local.SightEntity
import com.rogue.travelguru.data.mappers.toSightEntity
import retrofit2.HttpException
import java.io.IOException


@OptIn(ExperimentalPagingApi::class)
class FavouritesRemoteMediator(
    private val sightDb: SightDatabase,
    private val sightApi: SightPhotosRepository
): RemoteMediator<Int, SightEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, SightEntity>
    ): MediatorResult {
        return try {
            sightApi.getSightPhotos(1, 1)

            MediatorResult.Success(
                endOfPaginationReached = true
            )
        } catch(e: IOException) {
            MediatorResult.Error(e)
        } catch(e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}