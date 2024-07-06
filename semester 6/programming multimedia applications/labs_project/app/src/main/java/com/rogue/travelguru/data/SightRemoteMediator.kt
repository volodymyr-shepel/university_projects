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
class SightRemoteMediator(
    private val sightDb: SightDatabase,
    private val sightApi: SightPhotosRepository
): RemoteMediator<Int, SightEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, SightEntity>
    ): MediatorResult {
        return try {
            val loadKey = when(loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(
                    endOfPaginationReached = true
                )
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    if(lastItem == null) {
                        1
                    } else {
                        (lastItem.id / state.config.pageSize) + 1
                    }
                }
            }

            val sights = sightApi.getSightPhotos(
                page = loadKey,
                pageCount = state.config.pageSize
            )

            sightDb.withTransaction {
                if(loadType == LoadType.REFRESH) {
                    sightDb.dao.clearAll()
                }
                val sightEntities = sights.map { it.toSightEntity() }
                sightDb.dao.upsertAll(sightEntities)
            }

            MediatorResult.Success(
                endOfPaginationReached = sights.isEmpty()
            )
        } catch(e: IOException) {
            MediatorResult.Error(e)
        } catch(e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}