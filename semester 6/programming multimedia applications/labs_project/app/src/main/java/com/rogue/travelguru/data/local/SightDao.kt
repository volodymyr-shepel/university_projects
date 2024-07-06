package com.rogue.travelguru.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface SightDao {

    @Upsert
    fun upsertAll(sights: List<SightEntity>)

    @Query("SELECT * FROM sightentity")
    fun pagingSource(): PagingSource<Int, SightEntity>

    @Query("DELETE FROM sightentity")
    fun clearAll()

    @Query("SELECT * FROM sightentity WHERE sightentity.id = :arg0")
    fun getSightEntity(arg0: Int): SightEntity

    @Query("UPDATE sightentity SET favourite = 1 WHERE sightentity.id = :arg0")
    fun addToFavourites(arg0: Int): Unit

    @Query("UPDATE sightentity SET favourite = 0 WHERE sightentity.id = :arg0")
    fun removeFromFavourites(arg0: Int): Unit

    @Query("SELECT * FROM sightentity WHERE favourite = 1")
    fun pagingSourceForFavourites(): PagingSource<Int, SightEntity>
}